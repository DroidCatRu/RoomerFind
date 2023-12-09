plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "profileApi"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core.mvi)
            api(projects.feature.map.api)
            api(projects.feature.auth.api)
        }
    }
}

android {
    namespace = "ru.droidcat.feature.profile.api"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    kotlin {
        jvmToolchain(libs.versions.jvmTarget.get().toInt())
    }
}
