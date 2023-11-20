package io.astrum.mindsync.app.platform

import co.touchlab.kermit.Logger

class MindsyncEventTracker : Tracker {
    override fun onEventTracked(event: TrackEvents) {
        Logger.d { "Event: ${event.name}" }
    }
}
