package se.onemanstudio.test.umain.ui.screens.list.states

import se.onemanstudio.test.umain.ui.UiState

data class RestaurantDetailsContentState(
    val uiLogicState: UiState = UiState.Default,
    val isOpen: Boolean = true
)
