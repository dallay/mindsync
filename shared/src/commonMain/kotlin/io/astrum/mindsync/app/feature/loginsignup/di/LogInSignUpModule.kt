package io.astrum.mindsync.app.feature.loginsignup.di

import io.astrum.mindsync.app.data.repositories.AuthenticationRepository
import io.astrum.mindsync.app.data.usecases.LoadProfileUseCase
import io.astrum.mindsync.app.feature.loginsignup.usecases.EmailLogInUseCase
import io.astrum.mindsync.app.feature.loginsignup.usecases.EmailSignUpUseCase
import io.astrum.mindsync.app.feature.loginsignup.viewmodels.EmailLoginViewModel
import io.astrum.mindsync.app.feature.loginsignup.viewmodels.EmailSignUpViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val logInSignUpModule = module {
    factory {
        EmailLoginViewModel(get(), get(), get(), get(), get())
    }
    factory {
        EmailSignUpViewModel(get(), get(), get(), get(), get())
    }

    factoryOf(::EmailLogInUseCase)
    factoryOf(::EmailSignUpUseCase)
    factory { LoadProfileUseCase(get(), get()) }
    factory { AuthenticationRepository(get()) }
}
