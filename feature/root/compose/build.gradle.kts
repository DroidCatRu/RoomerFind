plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jb.compose)
}

kotlin {
    androidTarget()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "rootCompose"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.ui)
            implementation(projects.feature.root.api)
            implementation(projects.feature.auth.compose)
            implementation(projects.feature.main.compose)
        }
    }
}

android {
    namespace = "ru.droidcat.feature.root.compose"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    kotlin {
        jvmToolchain(libs.versions.jvmTarget.get().toInt())
    }
}
