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
            baseName = "mainApi"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.decompose)
            api(projects.feature.profile.api)
            api(projects.feature.finders.api)
        }
    }
}

android {
    namespace = "ru.droidcat.feature.main.api"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    kotlin {
        jvmToolchain(libs.versions.jvmTarget.get().toInt())
    }
}
