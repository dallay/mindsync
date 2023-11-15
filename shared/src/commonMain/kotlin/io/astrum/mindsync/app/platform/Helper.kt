package io.astrum.mindsync.app.platform

import io.astrum.mindsync.app.di.appModule
import org.koin.core.context.startKoin

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}
