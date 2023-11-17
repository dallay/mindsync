package io.astrum.mindsync.app.android

import android.app.Application
import io.astrum.mindsync.app.android.di.DependencyContainer

class MindSyncApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initDependencyContainer()
    }

    private fun initDependencyContainer() {
        DependencyContainer.initialize(this)
    }
}
