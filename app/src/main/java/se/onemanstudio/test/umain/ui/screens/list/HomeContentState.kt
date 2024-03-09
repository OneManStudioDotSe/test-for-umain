package se.onemanstudio.test.umain.ui.screens.list

import se.onemanstudio.test.umain.models.RestaurantEntry
import se.onemanstudio.test.umain.models.TagEntry
import se.onemanstudio.test.umain.ui.UiState

data class HomeContentState(
    val filters: List<TagEntry> = listOf(),
    val restaurants: List<RestaurantEntry> = listOf(),
    val uiLogicState: UiState = UiState.Default,
    val activeFilters: List<TagEntry> = listOf()
)
