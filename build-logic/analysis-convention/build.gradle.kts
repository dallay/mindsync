plugins {
    `kotlin-dsl`
}

group = "io.astrum.mindsync.buildlogic.analysis"
version = extra["app.plugins.version"].toString()

dependencies {
    implementation(libs.gradle.detekt)
    implementation(libs.gradle.ktlint)
    implementation(libs.gradle.ktlint.idea)
    implementation(libs.gradle.owasp.depcheck)
    implementation(project(":common"))
}
