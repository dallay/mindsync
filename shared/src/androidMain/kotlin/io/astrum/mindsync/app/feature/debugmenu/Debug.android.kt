package io.astrum.mindsync.app.feature.debugmenu

import com.russhwolf.settings.BuildConfig.DEBUG


class AndroidDebug : Debug {
    override val isDebug: Boolean = DEBUG
}

actual fun getDebug(): Debug = AndroidDebug()
