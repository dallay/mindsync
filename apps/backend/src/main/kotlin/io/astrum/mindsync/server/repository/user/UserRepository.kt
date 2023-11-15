package io.astrum.mindsync.server.repository.user

import io.astrum.mindsync.server.models.DatabaseUser

interface UserRepository {
    suspend fun addUser(
        email: String,
        name: String,
        passwordHash: String
    ): DatabaseUser?

    suspend fun findUser(userId: Int): DatabaseUser?
    suspend fun findUserByEmail(email: String): DatabaseUser?
}
