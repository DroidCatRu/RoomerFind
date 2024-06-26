plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jb.compose)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "15.0"
        podfile = project.file("../ios/Podfile")
        framework {
            baseName = "RoomerFind"
            isStatic = true
            export(libs.decompose)
            export(libs.essenty.lifecycle)
            export(libs.essenty.instancekeeper)
            export(libs.essenty.backhandler)
            export(projects.feature.map.api)
            export(projects.feature.root.api)
            export(projects.feature.root.internal)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.mvi)
            implementation(projects.core.ui)
            implementation(projects.feature.root.internal)
            implementation(projects.feature.root.compose)
            implementation(projects.feature.map.api)
            implementation(projects.feature.map.compose)
        }
        androidMain.dependencies {
            implementation(libs.maplibre)
        }
        iosMain.dependencies {
            api(libs.essenty.lifecycle)
            api(libs.essenty.instancekeeper)
            api(libs.essenty.backhandler)
            api(projects.feature.map.api)
            api(projects.feature.root.api)
            api(projects.feature.root.internal)
        }
    }
}

android {
    namespace = "ru.droidcat.roomerfindapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.javaVersion.get())
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    kotlin {
        jvmToolchain(libs.versions.jvmTarget.get().toInt())
    }
}
