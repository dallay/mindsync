package io.astrum.mindsync.app.data.usecases

import io.astrum.mindsync.app.common.model.PetModel
import io.astrum.mindsync.app.common.usecases.UseCase
import io.astrum.mindsync.app.data.repositories.LastSearchAdsMockRepository
import io.astrum.mindsync.app.feature.debugmenu.repositories.GlobalAppSettingsRepository
import io.astrum.mindsync.app.feature.search.repositories.LastSearchesRepository
import io.astrum.mindsync.app.feature.search.repositories.PetsFromSearchRepository

class GetLastSearchUseCase(
    private val lastSearchesRepository: LastSearchesRepository,
    private val petsFromSearchRepository: PetsFromSearchRepository,
    private val lastSearchAdsMockRepository: LastSearchAdsMockRepository,
    private val globalAppSettingsRepository: GlobalAppSettingsRepository
) : UseCase() {

    suspend fun invoke(): Result<List<PetModel>> = runCatching {
        val searchesList = lastSearchesRepository.get()
        return@runCatching if (globalAppSettingsRepository.isMockedContentEnabled()) {
            lastSearchAdsMockRepository.getAds().getOrDefault(emptyList())
        } else if (searchesList.list.isNotEmpty()) {
            petsFromSearchRepository.getPets(searchesList.list.last()).pets
        } else {
            emptyList()
        }
    }
}
