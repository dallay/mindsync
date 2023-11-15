package io.astrum.mindsync.app.feature.petupload.repositories

import io.astrum.mindsync.app.common.model.GeoLocation
import io.astrum.mindsync.app.common.model.PetAge
import io.astrum.mindsync.app.common.model.PetCategory
import io.astrum.mindsync.app.common.model.PetGender
import io.astrum.mindsync.app.common.model.PetResponse
import io.astrum.mindsync.app.common.model.PetSize
import io.astrum.mindsync.app.common.model.PetStatus
import io.astrum.mindsync.app.data.repositories.BadRequestException
import io.astrum.mindsync.app.data.repositories.UnauthorizedException
import io.astrum.mindsync.app.extensions.requestAndCatch
import io.astrum.mindsync.app.platform.ServerEnvironment
import io.astrum.mindsync.app.platform.ServiceClient
import io.ktor.client.request.forms.submitForm
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters

private const val PET_UPLOAD_PATH = "v1/pets/upload"
class PetUploadPublishRepository(private val service: ServiceClient) {

    suspend fun upload(
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
    ): PetResponse {
        return service.httpClient.requestAndCatch(
            {
                val response = this.submitForm(
                    url = "${ServerEnvironment.PRODUCTION.url}/$PET_UPLOAD_PATH",
                    formParameters = Parameters.build {
                        append("name", name)
                        append("description", description)
                        append("images", images.toString())
                        append("category", category.name)
                        append("location", location.toString())
                        append("breed", breed)
                        append("color", color)
                        append("age", age.name)
                        append("gender", gender.name)
                        append("size", size.name)
                        append("status", status.name)
                    }
                )
                PetResponse(
                    id = response.headers["pet_id"]?.toInt()!!
                )
            },
            {
                when (response.status) {
                    HttpStatusCode.Unauthorized -> {
                        throw UnauthorizedException()
                    }
                    HttpStatusCode.BadRequest -> {
                        throw BadRequestException()
                    }
                    else -> throw this
                }
            }
        )
    }
}
