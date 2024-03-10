package se.onemanstudio.test.umain.ui.screens.home.states

import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.models.TagEntry
import se.onemanstudio.test.umain.ui.common.UiState

data class HomeContentState(
    val uiLogicState: UiState = UiState.Default,
    val filters: List<TagEntry> = listOf(),
    val restaurants: List<RestaurantEntry> = listOf(),
    val activeFilters: List<TagEntry> = listOf()
)
