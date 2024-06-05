plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "mainInternal"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.mvi)
            api(projects.feature.main.api)
            implementation(projects.feature.profile.internal)
            implementation(projects.feature.finders.internal)
        }
    }
}

android {
    namespace = "ru.droidcat.feature.main.internal"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    kotlin {
        jvmToolchain(libs.versions.jvmTarget.get().toInt())
    }
}
