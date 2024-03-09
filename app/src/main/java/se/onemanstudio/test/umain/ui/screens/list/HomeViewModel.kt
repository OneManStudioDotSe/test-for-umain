package se.onemanstudio.test.umain.ui.screens.list

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
import se.onemanstudio.test.umain.ui.UiState
import se.onemanstudio.test.umain.utils.ContentUtils
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodDeliveryRepository: FoodDeliveryRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeContentState())
    val uiState: StateFlow<HomeContentState> = _uiState.asStateFlow()

    private var activeFilters = mutableListOf<TagEntry>()
    private var allRestaurants = mutableListOf<RestaurantEntry>()
    private var allFilters = mutableListOf<TagEntry>()
    private var allFilterIds = mutableListOf<String>()

    init {
        _uiState.update {
            it.copy(
                uiLogicState = UiState.Default
            )
        }
    }

    fun getRestaurants() {
        _uiState.update {
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
                    Timber.d("getRestaurants - on success")
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

                    Timber.d("------ RESTAURANTS ------")
                    allRestaurants.forEach {
                        Timber.d("Restaurant ${it.title} has ${it.tagsInitially.size} tags (${it.tags.size})")
                    }

                    // For each of the tags, fetch its info and store all their info at 'filters'
                    allFilterIds.distinct().forEach { item ->
                        foodDeliveryRepository.getFilterDetails(item)
                            .suspendOnSuccess {
                                Timber.d("getFilterDetails - on success for $item which has as title <${data.name}> and id ${data.id}")
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
                    Timber.d("------ FILTERS ------")
                    allFilters.forEach {
                        Timber.d("Filter ${it.title} has as id ${it.id}")
                    }

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
                            //Timber.d("Restaurant ${restaurant.title} will have ${restaurant.tags.size} tags")
                        }
                    }

                    Timber.d("------ RESTAURANTS ------")
                    allRestaurants.forEach {
                        Timber.d("Restaurant ${it.title} has ${it.tagsInitially.size} tags (${it.tags.size})")
                        it.tags.forEach { tag ->
                            Timber.d("--> ${tag.title} - ${tag.id}")
                        }
                    }

                    _uiState.update {
                        it.copy(
                            uiLogicState = UiState.Content,
                            restaurants = restaurants,
                            filters = filters,
                        )
                    }
                }
        }
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

        _uiState.update {
            it.copy(uiLogicState = UiState.Content, restaurants = filteredRestaurants)
        }
    }

    fun updateActiveFilters(newTag: TagEntry) {
        //Timber.d("I touched ${newTag.title} and the current active filters are:")
        activeFilters.forEachIndexed { index, tagEntry ->
            //Timber.d("Filter $index: ${tagEntry.title} - ${tagEntry.id}")
        }

        if (!activeFilters.contains(newTag)) {
            activeFilters.add(newTag)
            Timber.d("The new tag didn't exist already so I ADDED (+) it to the list ")
        } else {
            if (activeFilters.contains(newTag)) {
                activeFilters.remove(newTag)
                Timber.d("The new tag already existed so I REMOVED (-) it to the list")
            }
        }

        Timber.d("Updated active filters are:")
        Timber.d("---------------------------------------")
        activeFilters.forEachIndexed { index, tagEntry ->
            Timber.d("Filter $index: ${tagEntry.title} - ${tagEntry.id}")
        }
        Timber.d("---------------------------------------")

        _uiState.update {
            it.copy(
                uiLogicState = UiState.Content,
                activeFilters = activeFilters
            )
        }

        filterRestaurantsToShow()
    }
}