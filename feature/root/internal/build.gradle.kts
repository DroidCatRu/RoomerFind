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
            baseName = "rootInternal"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.mvi)
            api(projects.feature.root.api)
            implementation(projects.feature.auth.internal)
            implementation(projects.feature.main.internal)
        }
    }
}

android {
    namespace = "ru.droidcat.feature.root.internal"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    kotlin {
        jvmToolchain(libs.versions.jvmTarget.get().toInt())
    }
}
