package io.astrum.mindsync.app.feature.petupload.usecases

import io.astrum.mindsync.app.common.model.GeoLocation
import io.astrum.mindsync.app.common.model.PetAge
import io.astrum.mindsync.app.common.model.PetCategory
import io.astrum.mindsync.app.common.model.PetGender
import io.astrum.mindsync.app.common.model.PetSize
import io.astrum.mindsync.app.common.model.PetStatus
import io.astrum.mindsync.app.common.usecases.UseCase
import io.astrum.mindsync.app.feature.debugmenu.repositories.GlobalAppSettingsRepository
import io.astrum.mindsync.app.feature.petupload.repositories.PetUploadPublishRepository
import kotlin.random.Random

class PetUploadUseCase(
    private val petUploadPublishRepository: PetUploadPublishRepository,
    private val globalAppSettingsRepository: GlobalAppSettingsRepository
) : UseCase() {

    suspend fun invoke(
        name: String,
        description: String,
        images: List<String>,
        category: PetCategory,
        location: GeoLocation,
        breed: String,
        color: String,
        age: PetAge,
        gender: PetGender,
        size: PetSize,
        status: PetStatus
    ): Result<Int> = runCatching {
        if (!globalAppSettingsRepository.isMockedContentEnabled()) {
            val result = petUploadPublishRepository.upload(
                name, description, images, category, location, breed, color, age, gender, size, status
            )
            return@runCatching result.id
        } else {
            return@runCatching Random(10000).nextInt()
        }
    }
}
