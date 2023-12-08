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
            baseName = "coremMvi"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.koin.bom))
            api(libs.decompose)
            api(libs.mvikotlin)
            api(libs.mvikotlin.main)
            api(libs.mvikotlin.coroutines)
            api(libs.koin.core)
            api(libs.kotlinx.coroutines.core)
        }
        androidMain.dependencies {
            api(libs.koin.android)
            api(libs.kotlinx.coroutines.android)
        }
    }
}

android {
    namespace = "ru.droidcat.core.mvi"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    kotlin {
        jvmToolchain(libs.versions.jvmTarget.get().toInt())
    }
}
