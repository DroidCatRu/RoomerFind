plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.sqldelight)
    application
}

group = "ru.droidcat.roomerfind"
version = "0.0.1"

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("ru.droidcat.roomerfind.server.ApplicationKt")
}

sqldelight {
    databases {
        create("RoomerFindDb") {
            packageName.set("ru.droidcat.roomerfind.server")
            dialect(libs.sqldelight.postgresql)
        }
    }
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.ktor)

    implementation(libs.ktor.core)
    implementation(libs.ktor.netty)

    implementation(libs.hikari)

    implementation(libs.sqldelight.primitives)
    implementation(libs.sqldelight.postgresql)
    implementation(libs.sqldelight.jdbc.driver)
    implementation(libs.postgresql.driver)

    implementation(libs.uuid)
    implementation(libs.kotlin.datetime)

    implementation(projects.core.model)

    testImplementation(libs.kotlin.test)
}
