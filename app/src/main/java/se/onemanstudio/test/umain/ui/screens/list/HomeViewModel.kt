package se.onemanstudio.test.umain.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.models.TagEntry
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

            val allFilters = mutableListOf<String>()

            // Get all the restaurants and store them at 'restaurants'
            val content = foodDeliveryRepository.getRestaurants()

            // Store locally all the different tags that we come across
            content.restaurants.forEach {
                Timber.d("I found restaurant ${it.name} that has ${it.filterIds.size} filters")

                restaurants.add(
                    RestaurantEntry(
                        title = it.name!!,
                        promoImageUrl = it.imageUrl!!,
                        tags = listOf(),
                        tagsInitially = it.filterIds,
                        rating = it.rating!!,
                        openTimeAsText = ContentUtils.convertDeliveryTimeToReadableForm(it.deliveryTimeMinutes!!)
                    )
                )

                allFilters.addAll(it.filterIds)
            }

            // For each of the tags, fetch its info and store all their info at 'filters'
            allFilters.distinct().forEach {
                val filterDetails = foodDeliveryRepository.getFilterDetails(it)

                Timber.d("Filter $it has as title <${filterDetails.name}> and url ${filterDetails.imageUrl}")

                filters.add(
                    TagEntry(
                        id = filterDetails.id!!,
                        title = filterDetails.name!!,
                        tagImageUrl = filterDetails.imageUrl!!
                    )
                )
            }

            // Now that we have all the info we need for the tags, let's update the list of restaurants with the details of their tags
            restaurants.forEach { restaurant ->
                restaurant.tagsInitially.forEach { initialTagId ->
                    val tagsWithDetails = mutableListOf<TagEntry>()

                    filters.distinct().forEach { tagEntry ->
                        if (initialTagId == tagEntry.id) {
                            tagsWithDetails.add(tagEntry)
                        }
                    }

                    restaurant.tags =
                        tagsWithDetails //TODO: this is bad. Fix it so all fields of RestaurantEntry are immutable

                    Timber.d("Restaurant ${restaurant.title} will have ${restaurant.tags.size} tags")
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