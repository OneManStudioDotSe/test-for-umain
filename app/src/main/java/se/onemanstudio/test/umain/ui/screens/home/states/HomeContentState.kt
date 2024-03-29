package se.onemanstudio.test.umain.ui.screens.home.states

import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.models.TagEntry
import se.onemanstudio.test.umain.ui.common.UiState

data class HomeContentState(
    val uiLogicState: UiState,
    val filters: List<TagEntry>,
    val restaurants: List<RestaurantEntry>,
    val activeFilters: List<TagEntry>
)
