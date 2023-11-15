package io.astrum.mindsync.app.data.usecases

import io.astrum.mindsync.app.common.usecases.UseCase
import io.astrum.mindsync.app.data.repositories.ProfileRepository
import io.astrum.mindsync.app.data.repositories.SessionRepository

class LoadProfileUseCase(
    private val profileRepository: ProfileRepository,
    private val sessionRepository: SessionRepository
) : UseCase() {

    suspend fun invoke(userId: Int) {
        if (sessionRepository.isLoggedIn()) {
            val profile = profileRepository.getProfile(userId)
            profileRepository.initProfile(userId, profile.name, profile.description, profile.image, profile.location, profile.rating)
        }
    }
}
