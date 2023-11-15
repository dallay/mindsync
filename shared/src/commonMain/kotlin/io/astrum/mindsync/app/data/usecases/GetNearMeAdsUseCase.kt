package io.astrum.mindsync.app.data.usecases

import io.astrum.mindsync.app.common.model.PetModel
import io.astrum.mindsync.app.common.model.SearchModel
import io.astrum.mindsync.app.common.usecases.UseCase
import io.astrum.mindsync.app.data.repositories.NearMeAdsMockRepository
import io.astrum.mindsync.app.data.repositories.ProfileRepository
import io.astrum.mindsync.app.feature.debugmenu.repositories.GlobalAppSettingsRepository
import io.astrum.mindsync.app.feature.search.repositories.PetsFromSearchRepository

class GetNearMeAdsUseCase(
    private val profileRepository: ProfileRepository,
    private val petsFromSearchRepository: PetsFromSearchRepository,
    private val nearMeAdsMockRepository: NearMeAdsMockRepository,
    private val globalAppSettingsRepository: GlobalAppSettingsRepository
) : UseCase() {

    suspend fun invoke(): Result<List<PetModel>> = runCatching {
        val location = profileRepository.getLocation()
        return@runCatching if (globalAppSettingsRepository.isMockedContentEnabled()) {
            nearMeAdsMockRepository.getAds().getOrDefault(emptyList())
        } else if (location != null) {
            val searchModel = SearchModel(location = location)
            petsFromSearchRepository.getPets(searchModel).pets
        } else {
            emptyList()
        }
    }
}
