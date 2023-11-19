plugins {
    `kotlin-dsl`
}

group = "io.astrum.mindsync.buildlogic.documentation"
version = extra["app.plugins.version"].toString()

dependencies {
    implementation(libs.gradle.owasp.depcheck)
    implementation(libs.gradle.detekt)
    implementation(libs.gradle.kotlin)
    implementation(libs.gradle.dokka)
    implementation(libs.gradle.asciidoctor)
    implementation(project(":common"))
}
