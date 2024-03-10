package se.onemanstudio.test.umain.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skydoves.sandwich.messageOrNull
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.retrofit.serialization.onErrorDeserialize
import com.skydoves.sandwich.retrofit.statusCode
import com.skydoves.sandwich.suspendOnSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.models.TagEntry
import se.onemanstudio.test.umain.network.dto.FilterErrorResponse
import se.onemanstudio.test.umain.repository.FoodDeliveryRepository
import se.onemanstudio.test.umain.ui.common.UiState
import se.onemanstudio.test.umain.ui.screens.home.states.HomeContentState
import se.onemanstudio.test.umain.ui.screens.home.states.RestaurantDetailsContentState
import se.onemanstudio.test.umain.utils.ContentUtils
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodDeliveryRepository: FoodDeliveryRepository,
) : ViewModel() {

    private val _uiHomeState = MutableStateFlow(HomeContentState())
    val uiHomeState: StateFlow<HomeContentState> = _uiHomeState.asStateFlow()

    private val _uiRestaurantDetailsState = MutableStateFlow(RestaurantDetailsContentState())
    val uiRestaurantDetailsState: StateFlow<RestaurantDetailsContentState> = _uiRestaurantDetailsState.asStateFlow()

    private var activeFilters = mutableListOf<TagEntry>()
    private var allRestaurants = mutableListOf<RestaurantEntry>()
    private var allFilters = mutableListOf<TagEntry>()
    private var allFilterIds = mutableListOf<String>()

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
            val restaurants = mutableListOf<RestaurantEntry>()
            val filters = mutableListOf<TagEntry>()

            // Get all the restaurants and store them at 'restaurants'
            foodDeliveryRepository.getRestaurants()
                .suspendOnSuccess {
                    // Store locally all the different tags that we come across
                    data.restaurants.forEach {
                        restaurants.add(
                            RestaurantEntry(
                                id = it.id!!,
                                title = it.name!!,
                                promoImageUrl = it.imageUrl!!,
                                tags = listOf(),
                                tagsInitially = it.filterIds,
                                rating = it.rating!!,
                                openTimeAsText = ContentUtils.convertDeliveryTimeToReadableForm(it.deliveryTimeMinutes!!)
                            )
                        )

                        allFilterIds.addAll(it.filterIds)
                    }

                    allRestaurants = restaurants

                    // For each of the tags, fetch its info and store all their info at 'filters'
                    allFilterIds.distinct().forEach { item ->
                        foodDeliveryRepository.getFilterDetails(item)
                            .suspendOnSuccess {
                                filters.add(
                                    TagEntry(
                                        id = data.id!!,
                                        title = data.name!!,
                                        tagImageUrl = data.imageUrl!!
                                    )
                                )
                            }
                            .onError {
                                onErrorDeserialize<String, FilterErrorResponse> { errorMessage ->
                                    Timber.e("getFilterDetails - Status code: $statusCode -> ${statusCode.code} and error: ${errorMessage.error}, reason: ${errorMessage.reason}")
                                }
                            }
                            .onException {
                                Timber.d("getFilterDetails - on exception for $item -> $messageOrNull")
                            }
                    }

                    allFilters = filters

                    // Now that we have all the info we need for the tags, let's update the list of restaurants with the details of their tags
                    allRestaurants.forEach { restaurant ->
                        val tagsWithDetails = mutableListOf<TagEntry>()
                        restaurant.tagsInitially.forEach { initialTagId ->

                            allFilters.forEach { tagEntry ->
                                if (initialTagId == tagEntry.id) {
                                    tagsWithDetails.add(tagEntry)
                                }
                            }

                            restaurant.tags = tagsWithDetails //TODO: This is bad. Make all of RestaurantEntry immutable
                        }
                    }

                    /*
                    Timber.d("------ RESTAURANTS ------")
                    allRestaurants.forEach {
                        Timber.d("Restaurant ${it.title} has ${it.tagsInitially.size} tags (${it.tags.size})")
                        it.tags.forEach { tag ->
                            Timber.d("--> ${tag.title} - ${tag.id}")
                        }
                    }
                     */

                    _uiHomeState.update {
                        it.copy(
                            uiLogicState = UiState.Content,
                            restaurants = restaurants,
                            filters = filters,
                        )
                    }
                }
                .onError {
                    _uiHomeState.update {
                        it.copy(uiLogicState = UiState.Error)
                    }
                }
                .onException {
                    _uiHomeState.update {
                        it.copy(uiLogicState = UiState.Error)
                    }
                }
        }
    }

    fun getOpenStatus(restaurantId: String) {
        viewModelScope.launch {
            foodDeliveryRepository.getOpenStatusForRestaurant(restaurantId)
                .suspendOnSuccess {
                    _uiRestaurantDetailsState.update {
                        it.copy(
                            uiLogicState = UiState.Content,
                            isOpen = data.isCurrentlyOpen!!
                        )
                    }
                }
                .onError {
                    _uiRestaurantDetailsState.update {
                        it.copy(uiLogicState = UiState.Error)
                    }

                }
                .onException {
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
}