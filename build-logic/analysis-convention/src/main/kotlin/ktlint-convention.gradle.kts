plugins {
    id("org.jlleitschuh.gradle.ktlint")
    id("org.jlleitschuh.gradle.ktlint-idea")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    version.set(libs.findVersion("klint").get().toString())
    debug.set(true)
    verbose.set(true)
    android.set(false)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(true)
    enableExperimentalRules.set(true)
}


