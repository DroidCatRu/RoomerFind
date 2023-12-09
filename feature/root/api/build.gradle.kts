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
            baseName = "rootApi"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.decompose)
            api(projects.feature.auth.api)
            api(projects.feature.profile.api)
            api(projects.feature.finders.api)
        }
    }
}

android {
    namespace = "ru.droidcat.feature.root.api"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    kotlin {
        jvmToolchain(libs.versions.jvmTarget.get().toInt())
    }
}
