package se.onemanstudio.test.umain.ui

sealed class UiState {
    data object Default : UiState()
    data object Content : UiState()
    data object Error : UiState()
    data object Loading : UiState()
}
