plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "io.astrum.mindsync.app.android"
    compileSdk = properties["mindsync.android.compileSdk"].toString().toInt()
    defaultConfig {
        applicationId = "io.astrum.mindsync.app.android"
        minSdk = properties["mindsync.android.minSdk"].toString().toInt()
        targetSdk = properties["mindsync.android.targetSdk"].toString().toInt()
        versionName = "1.0"
    }
    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/kotlin")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/versions/9/previous-compilation-data.bin"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":shared"))

    implementation(platform(libs.compose.bom))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.tracing.ktx)

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class")
    implementation("androidx.compose.ui:ui-tooling-preview")

    implementation(libs.androidx.activity.compose)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    implementation(platform(libs.firebase.bom))
    implementation("com.google.firebase:firebase-analytics-ktx")

    testImplementation(libs.junit)
    testImplementation(libs.koin.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
}
