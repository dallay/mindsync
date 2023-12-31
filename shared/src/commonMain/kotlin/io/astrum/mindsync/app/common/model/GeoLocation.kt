package io.astrum.mindsync.app.common.model

import kotlinx.serialization.Serializable

@Serializable
data class GeoLocation(val latitude: Double, val longitude: Double)
