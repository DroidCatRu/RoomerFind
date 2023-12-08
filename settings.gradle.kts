enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "RoomerFind"
include(":server")
include(":core:model")
include(":roomerFindApp")
include(":feature:auth:api")
include(":feature:auth:internal")
include(":feature:auth:compose")
include(":core:ui")
include(":core:mvi")
