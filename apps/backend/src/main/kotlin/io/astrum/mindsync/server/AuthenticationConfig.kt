@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.astrum.mindsync.server

import io.astrum.mindsync.server.auth.JWT_CONFIGURATION
import io.astrum.mindsync.server.auth.JwtService
import io.astrum.mindsync.server.auth.hash
import io.astrum.mindsync.server.repository.DatabaseFactory
import io.astrum.mindsync.server.repository.pets.PetsRepositoryImp
import io.astrum.mindsync.server.repository.profile.ProfileRepositoryImpl
import io.astrum.mindsync.server.repository.user.UserRepositoryImp
import io.astrum.mindsync.server.routes.pets
import io.astrum.mindsync.server.routes.profiles
import io.astrum.mindsync.server.routes.users
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.locations.KtorExperimentalLocationsAPI
import io.ktor.server.routing.routing

fun Application.configureAuthentication() {
    DatabaseFactory.init()
    val userRepository = UserRepositoryImp()
    val petRepository = PetsRepositoryImp()
    val profileRepository = ProfileRepositoryImpl()
    val jwtService = JwtService()
    val hashFunction = { s: String -> hash(s) }

    install(Authentication) {
        jwt(JWT_CONFIGURATION) {
            verifier(jwtService.verifier)
            realm = "MyProjectName Server"
            validate {
                val payload = it.payload
                val claim = payload.getClaim("id")
                val claimString = claim.asInt()
                val user = userRepository.findUser(claimString)
                user
            }
        }
    }

    routing {
        users(userRepository, jwtService, hashFunction)
        pets(petRepository, userRepository)
        profiles(profileRepository, userRepository)
    }
}
