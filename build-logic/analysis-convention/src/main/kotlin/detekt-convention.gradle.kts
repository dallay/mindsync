import io.astrum.mindsync.buildlogic.common.AppConfiguration
import io.astrum.mindsync.buildlogic.common.extensions.catalogLib
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    toolVersion = "1.23.3"
    parallel = true
    ignoreFailures = false
    autoCorrect = true
    buildUponDefaultConfig = true
    config.setFrom(files("$rootDir/config/detekt.yml"))
}

tasks.withType<Detekt>().configureEach {
    jvmTarget = AppConfiguration.jvmTargetStr
    setSource(files(projectDir))
    include("**/*.kt", "**/*.kts")
    exclude("**/resources/**", "**/build/**")
    reports {
        xml.required.set(true)
        html.required.set(true)
        txt.required.set(true)
        sarif.required.set(true)
        md.required.set(true)
    }
}


dependencies {
    detektPlugins(catalogLib("detekt-compose"))
    detektPlugins(catalogLib("detekt-compose2"))
    detektPlugins(catalogLib("detekt-formatting"))
}
