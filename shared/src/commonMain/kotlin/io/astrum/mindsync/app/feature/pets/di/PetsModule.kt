package io.astrum.mindsync.app.feature.pets.di

import cafe.adriel.voyager.navigator.Navigator
import io.astrum.mindsync.app.feature.pets.viewmodels.MyPetsViewModel
import org.koin.dsl.module

val petsModule = module {
    factory { (navigator: Navigator) ->
        MyPetsViewModel(navigator)
    }
}
