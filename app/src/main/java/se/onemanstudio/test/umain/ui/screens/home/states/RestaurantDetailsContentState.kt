package se.onemanstudio.test.umain.ui.screens.home.states

import se.onemanstudio.test.umain.ui.common.UiState

data class RestaurantDetailsContentState(
    val uiLogicState: UiState = UiState.Default,
    val isOpen: Boolean = true
)
