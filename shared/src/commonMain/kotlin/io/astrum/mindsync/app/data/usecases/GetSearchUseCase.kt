package io.astrum.mindsync.app.data.usecases

import io.astrum.mindsync.app.common.model.PetCategory
import io.astrum.mindsync.app.common.model.PetModel
import io.astrum.mindsync.app.common.usecases.UseCase

class GetSearchUseCase(
    private val nearMeAdsUseCase: GetNearMeAdsUseCase
) : UseCase() {

    suspend fun invoke(searchId: Int): Result<List<PetModel>> = runCatching {
        return@runCatching emptyList()
    }

    suspend fun invoke(petCategory: PetCategory): Result<List<PetModel>> = runCatching {
        return@runCatching nearMeAdsUseCase.invoke().getOrDefault<List<PetModel>, List<PetModel>>(emptyList<PetModel>())
            .filter<PetModel> { it.category == petCategory }
    }
}
