package io.astrum.mindsync.server

import io.astrum.mindsync.server.plugins.configureAdministration
import io.astrum.mindsync.server.plugins.configureHTTP
import io.astrum.mindsync.server.plugins.configureMonitoring
import io.astrum.mindsync.server.plugins.configureSerialization
import io.astrum.mindsync.server.plugins.configureTemplating
import io.ktor.server.engine.embeddedServer
import io.ktor.server.tomcat.Tomcat

fun main() {
    embeddedServer(Tomcat, port = 8080, host = "0.0.0.0") {
        configureSessions()
        configureGeneralRouting()
        configureAuthentication()
        configureHTTP()
        configureMonitoring()
        configureTemplating()
        configureSerialization()
        configureAdministration()
    }.start(wait = true)
}

const val API_VERSION = "/v1"
