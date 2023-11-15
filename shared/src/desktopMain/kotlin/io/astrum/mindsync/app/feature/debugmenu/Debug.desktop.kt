package io.astrum.mindsync.app.feature.debugmenu

import io.astrum.mindsync.app.feature.debugmenu.Debug

class DesktopDebug : Debug {
    override val isDebug: Boolean
        get() = true
}

actual fun getDebug(): Debug = DesktopDebug()
