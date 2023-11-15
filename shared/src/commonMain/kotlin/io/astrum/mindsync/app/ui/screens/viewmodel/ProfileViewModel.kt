package io.astrum.mindsync.app.ui.screens.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import io.astrum.mindsync.app.common.model.GeoLocation
import io.astrum.mindsync.app.data.repositories.ProfileRepository
import io.astrum.mindsync.app.data.repositories.SessionRepository
import io.astrum.mindsync.app.feature.loginsignup.LoginSignUpLandingScreen
import io.astrum.mindsync.app.feature.pets.MyPetsScreen
import io.astrum.mindsync.app.feature.profile.ProfileDetailScreen
import io.astrum.mindsync.app.navigation.HomeTab
import io.astrum.mindsync.app.platform.RootNavigatorRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private var navigator: Navigator,
    private val profileRepository: ProfileRepository,
    private val sessionRepository: SessionRepository,
    private val rootNavigatorRepository: RootNavigatorRepository
) : ScreenModel {
    private val _state = MutableStateFlow(ProfileState("", "", "", null, 0.0, false))
    val state = _state.asStateFlow()
    private val _sideEffects = Channel<ProfileSideEffects>()
    val sideEffects: Flow<ProfileSideEffects> = _sideEffects.receiveAsFlow()

    init {
        if (!sessionRepository.isLoggedIn()) {
            rootNavigatorRepository.navigator.push(LoginSignUpLandingScreen())
        }
    }

    fun onProfileDetailClicked() {
        rootNavigatorRepository.navigator.push(ProfileDetailScreen(sessionRepository.getUserId()))
    }

    fun onMyPetsClicked() {
        rootNavigatorRepository.navigator.push(MyPetsScreen())
    }

    fun onProfileAccountSettingsClicked() {
    }

    fun onProfileSettingsClicked() {
    }

    fun onBlogClicked() {
    }

    fun onTermsAndConditionsClicked() {
    }

    fun onHelpClicked() {
    }

    fun onCloseSessionClicked() {
        // TODO: Logout on service
        sessionRepository.clear()
        rootNavigatorRepository.tabNavigator.current = HomeTab
    }

    fun onStarted(navigator: Navigator) {
        this.navigator = navigator
        screenModelScope.launch {
            _state.value = ProfileState(
                name = profileRepository.getName(),
                description = profileRepository.getDescription(),
                image = profileRepository.getImage(),
                location = profileRepository.getLocation(),
                rating = profileRepository.getRating(),
                isLoggedIn = sessionRepository.isLoggedIn()
            )
        }
    }

    fun onSignUpLoginClicked() {
        rootNavigatorRepository.navigator.push(LoginSignUpLandingScreen())
    }
}

data class ProfileState(
    val name: String,
    val description: String,
    val image: String,
    val location: GeoLocation?,
    val rating: Double,
    val isLoggedIn: Boolean
)

sealed class ProfileSideEffects {
    data object Initial : ProfileSideEffects()
    data object OnSignedUp : ProfileSideEffects()
    data object OnSignUpError : ProfileSideEffects()
}
