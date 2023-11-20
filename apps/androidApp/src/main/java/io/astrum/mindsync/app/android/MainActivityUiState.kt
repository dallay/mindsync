package io.astrum.mindsync.app.android

import io.astrum.mindsync.app.common.model.UserData

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
