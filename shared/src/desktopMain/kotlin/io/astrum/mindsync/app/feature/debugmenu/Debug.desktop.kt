package io.astrum.mindsync.app.feature.debugmenu

class DesktopDebug : Debug {
    override val isDebug: Boolean
        get() = true
}

actual fun getDebug(): Debug = DesktopDebug()
