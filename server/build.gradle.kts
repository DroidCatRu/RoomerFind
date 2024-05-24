plugins {
    application
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
}

group = "ru.droidcat.roomerfind"
version = "0.0.1"

kotlin {
    jvmToolchain(libs.versions.jvmTarget.get().toInt())
}

application {
    mainClass.set("ru.droidcat.roomerfind.server.ApplicationKt")
}

dependencies {

    implementation(libs.postgresql.driver)

    implementation(libs.uuid)

    testImplementation(libs.kotlin.test)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.content.negotiation)

    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.serialization.json)

    implementation(libs.ktor.server.auth)

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)

    implementation(libs.jbcrypt)
}
