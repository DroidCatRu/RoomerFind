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
include(":core:mvi")
include(":core:ui")

include(":feature:auth:api")
include(":feature:auth:internal")
include(":feature:auth:compose")

include(":feature:root:api")
include(":feature:root:internal")
include(":feature:root:compose")

include(":feature:map:api")
include(":feature:map:internal")
include(":feature:map:compose")

include(":roomerFindApp")
include(":feature:profile:api")
include(":feature:profile:internal")
include(":feature:profile:compose")
include(":feature:finders:api")
include(":feature:finders:internal")
include(":feature:finders:compose")
