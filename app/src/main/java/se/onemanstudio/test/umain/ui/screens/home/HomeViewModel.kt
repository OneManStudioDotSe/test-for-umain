package se.onemanstudio.test.umain.ui.screens.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.retrofit.serialization.onErrorDeserialize
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import se.onemanstudio.test.umain.R
import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.models.TagEntry
import se.onemanstudio.test.umain.network.dto.FilterErrorResponse
import se.onemanstudio.test.umain.network.dto.OpenStatusErrorResponse
import se.onemanstudio.test.umain.repository.FoodDeliveryRepository
import se.onemanstudio.test.umain.ui.common.UiState
import se.onemanstudio.test.umain.ui.common.views.OpenStatus
import se.onemanstudio.test.umain.ui.screens.home.states.HomeContentState
import se.onemanstudio.test.umain.ui.screens.home.states.RestaurantDetailsContentState
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val foodDeliveryRepository: FoodDeliveryRepository,
) : ViewModel() {

    private val _uiHomeState = MutableStateFlow(
        HomeContentState(
            uiLogicState = UiState.Default,
            activeFilters = listOf(),
            filters = listOf(),
            restaurants = listOf()
        )
    )
    val uiHomeState: StateFlow<HomeContentState> = _uiHomeState.asStateFlow()

    private val _uiRestaurantDetailsState =
        MutableStateFlow(
            RestaurantDetailsContentState(
                uiLogicState = UiState.Default,
                openStatus = OpenStatus.UNKNOWN
            )
        )
    val uiRestaurantDetailsState: StateFlow<RestaurantDetailsContentState> = _uiRestaurantDetailsState.asStateFlow()

    private var activeFilters = mutableListOf<TagEntry>()
    private var allRestaurants = mutableListOf<RestaurantEntry>()

    init {
        _uiHomeState.update {
            it.copy(
                uiLogicState = UiState.Default
            )
        }
    }

    fun getRestaurants() {
        _uiHomeState.update {
            it.copy(
                uiLogicState = UiState.Loading
            )
        }

        viewModelScope.launch {
            val filters = mutableListOf<TagEntry>()
            var allFilterIds: MutableList<String>
            val restaurants = mutableListOf<RestaurantEntry>()

            // Get all the restaurants and save them locally
            foodDeliveryRepository.getRestaurants()
                .suspendOnSuccess {
                    // Store locally all the different tags that we come across
                    allFilterIds = data.restaurants.flatMap { it.filterIds }.toMutableList()

                    // For each of the tags, fetch its info and store all their info at 'filters'
                    allFilterIds.distinct().forEach { item ->
                        foodDeliveryRepository.getFilterDetails(item)
                            .suspendOnSuccess {
                                filters.add(
                                    TagEntry(
                                        id = data.id ?: "",
                                        title = data.name ?: "",
                                        tagImageUrl = data.imageUrl ?: ""
                                    )
                                )
                            }
                            .onError {
                                onErrorDeserialize<String, FilterErrorResponse> { errorMessage ->
                                    handleError(errorMessage.reason)
                                }
                            }
                            .onException {
                                handleError(messageOrNull)
                            }
                    }

                    data.restaurants.forEach {
                        val tagsWithDetails = mutableListOf<TagEntry>()
                        it.filterIds.forEach { initialTagId ->

                            filters.forEach { tagEntry ->
                                if (initialTagId == tagEntry.id) {
                                    tagsWithDetails.add(tagEntry)
                                }
                            }
                        }

                        restaurants.add(
                            RestaurantEntry(
                                id = it.id ?: "",
                                title = it.name ?: "",
                                promoImageUrl = it.imageUrl ?: "",
                                tags = tagsWithDetails,
                                tagsInitially = it.filterIds,
                                rating = it.rating ?: 5.0,
                                openTimeAsText = it.deliveryTimeMinutes?.convertDeliveryTimeToReadableForm(appContext)
                                    ?: ""
                            )
                        )

                        allFilterIds.addAll(it.filterIds)
                    }

                    allRestaurants = restaurants

                    _uiHomeState.update {
                        it.copy(
                            uiLogicState = UiState.Content,
                            restaurants = allRestaurants,
                            filters = filters,
                        )
                    }
                }
                .onError {
                    handleError(messageOrNull)

                    _uiHomeState.update {
                        it.copy(uiLogicState = UiState.Error)
                    }
                }
                .onException {
                    handleError(messageOrNull)

                    _uiHomeState.update {
                        it.copy(uiLogicState = UiState.Error)
                    }
                }
        }
    }

    fun getOpenStatus(restaurantId: String) {
        _uiRestaurantDetailsState.update {
            it.copy(
                uiLogicState = UiState.Loading,
            )
        }

        viewModelScope.launch {
            foodDeliveryRepository.getOpenStatusForRestaurant(restaurantId)
                .suspendOnSuccess {
                    _uiRestaurantDetailsState.update {
                        it.copy(
                            uiLogicState = UiState.Content,
                            openStatus = when (data.isCurrentlyOpen) {
                                null -> OpenStatus.UNKNOWN
                                true -> OpenStatus.OPEN
                                false -> OpenStatus.CLOSED
                            },
                        )
                    }
                }
                .onError {
                    onErrorDeserialize<String, OpenStatusErrorResponse> { errorMessage ->
                        handleError(errorMessage.reason)
                    }

                    _uiRestaurantDetailsState.update {
                        it.copy(uiLogicState = UiState.Error)
                    }

                }
                .onException {
                    handleError(messageOrNull)

                    _uiRestaurantDetailsState.update {
                        it.copy(uiLogicState = UiState.Error)
                    }
                }
        }
    }

    fun updateActiveFilters(newTag: TagEntry) {
        if (!activeFilters.contains(newTag)) {
            activeFilters.add(newTag)
        } else {
            if (activeFilters.contains(newTag)) {
                activeFilters.remove(newTag)
            }
        }

        /*
        Timber.d("Updated active filters are:")
        Timber.d("---------------------------------------")
        activeFilters.forEachIndexed { index, tagEntry ->
            Timber.d("Filter $index: ${tagEntry.title} - ${tagEntry.id}")
        }
        Timber.d("---------------------------------------")
        */

        _uiHomeState.update {
            it.copy(
                uiLogicState = UiState.Content,
                activeFilters = activeFilters
            )
        }

        filterRestaurantsToShow()
    }

    private fun filterRestaurantsToShow() {
        val filteredRestaurants = if (activeFilters.isEmpty()) {
            allRestaurants
        } else {
            allRestaurants.filter { restaurant ->
                restaurant.tags.any { tag ->
                    activeFilters.contains(tag)
                }
            }
        }

        _uiHomeState.update {
            it.copy(uiLogicState = UiState.Content, restaurants = filteredRestaurants)
        }
    }

    private fun Int.convertDeliveryTimeToReadableForm(context: Context): String {
        val min = this.toDuration(DurationUnit.MINUTES)

        min.toComponents { days, hours, minutes, _, _ ->
            // Ignore seconds and nanoseconds
            return when {
                days >= 1 -> context.getString(R.string.delivery_in_days)
                hours >= 1 -> {
                    if (hours == 1) context.resources.getQuantityString(R.plurals.delivery_hour, 1)
                    else String.format(context.resources.getQuantityString(R.plurals.delivery_hour, hours), hours)
                }

                minutes > 1 -> String.format(
                    context.resources.getQuantityString(R.plurals.delivery_min, minutes),
                    minutes
                )

                else -> context.resources.getQuantityString(R.plurals.delivery_min, 1)
            }
        }
    }

    private fun handleError(message: String?) {
        Timber.d("An error occurred: $message")
    }
}