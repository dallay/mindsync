import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        jvmToolchain(17)
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.skiko)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.animation)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation(libs.skiko.macos.arm64)
                implementation(project(":shared"))

                implementation(libs.androidx.compose.ui.util)

                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.coroutines.swingui)
                implementation(libs.ktor.client)
                implementation(libs.ktor.client.java)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.server.serialization.kotlinx.json)

                // Kamel for image loading
                implementation(libs.kamel)

                // Voyager for Navigation
                implementation(libs.voyager.navigator)
                implementation(libs.voyager.bottom.sheet.navigator)
                implementation(libs.voyager.tab.navigator)
                implementation(libs.voyager.transitions)
                implementation(libs.voyager.koin)

                // Multiplatform Settings to encrypted key-value data
                implementation(libs.multiplatform.settings.no.arg)
                implementation(libs.multiplatform.settings.serialization)

                // Dependency Injection
                implementation(libs.koin.core)
                implementation(libs.koin.test)
                implementation(libs.koin.compose)

                // Logging
                implementation(libs.kermit)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "io.astrum.mindsync.app"
            packageVersion = "1.0.0"
            copyright = "Copyright (c) 2021"
            vendor = "Astrum"

            val iconsRoot = project.file("src/desktopMain/resources/drawable")

            macOS{
                bundleID = "io.astrum.kmptemplate"
                iconFile.set(iconsRoot.resolve("desktop-app.icns"))
            }

            windows {
                iconFile.set(iconsRoot.resolve("desktop-app.ico"))
                // https://www.guidgen.com/
                upgradeUuid = "1e5dc996-79e8-4d3a-8e0e-04019db777bd"
            }

            linux {
                iconFile.set(iconsRoot.resolve("desktop-app.png"))
            }
        }
    }
}
