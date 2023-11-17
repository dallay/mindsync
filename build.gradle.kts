plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    kotlin("android").version(libs.versions.kotlin).apply(false)
    kotlin("multiplatform").version(libs.versions.kotlin).apply(false)
    kotlin("plugin.serialization").version(libs.versions.kotlin).apply(false)
    alias(libs.plugins.compose.multiplatform).apply(false)
    id("detekt-convention")
    id("security-owasp-convention")
    id("documentation-consumer-convention")
}

/* this task generates all tasks for sub-projects itself, therefor it just needs
 to be applied on the root project, conventions are not working :-( */
tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(layout.buildDirectory.dir("dokka"))
}

dependencies {
    asciidoc(project(":documentation"))
}
