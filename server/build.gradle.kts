plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    application
}

group = "ru.droidcat.roomerfind"
version = "0.0.1"

kotlin {
    jvmToolchain(libs.versions.jvmTarget.get().toInt())
}

application {
    mainClass.set("ru.droidcat.roomerfind.server.ApplicationKt")
}

//sqldelight {
//    databases {
//        create("RoomerFindDb") {
//            packageName.set("ru.droidcat.roomerfind.server")
//            dialect(libs.sqldelight.postgresql)
//        }
//    }
//}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.ktor)

    implementation(libs.postgresql.driver)

    implementation(libs.uuid)
    implementation(libs.kotlin.datetime)

    implementation(projects.core.model)

    testImplementation(libs.kotlin.test)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.content.negotiation)

    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.serialization.json)

    implementation(libs.ktor.server.auth)

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)

//    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation(libs.jbcrypt)
}
