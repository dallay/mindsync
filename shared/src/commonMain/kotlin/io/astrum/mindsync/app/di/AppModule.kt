package io.astrum.mindsync.app.di

import io.astrum.mindsync.app.feature.loginsignup.di.logInSignUpModule
import io.astrum.mindsync.app.feature.pets.di.petsModule

fun appModule() = listOf(
    commonModule,
    logInSignUpModule,
    petsModule
)
