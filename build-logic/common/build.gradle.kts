plugins {
    `kotlin-dsl`
}

group = "io.astrum.mindsync.buildlogic.common"
version = extra["app.plugins.version"].toString()


dependencies {
//    implementation(libs.gradle.compose)
//    implementation(libs.gradle.android)
//    implementation(libs.gradle.sentry)
    implementation(libs.gradle.kotlin)
//    implementation(libs.gradle.kotlin.serialization)
//    implementation(libs.gradle.devtools.ksp)
}
