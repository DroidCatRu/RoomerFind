val ktor_version: String by project
val exposed_version: String by project

plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.sqldelight)
    application

    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.21"
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



    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")

    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    implementation("io.ktor:ktor-server-auth:$ktor_version")

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

    implementation("org.postgresql:postgresql:42.6.0")
//    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation("org.mindrot:jbcrypt:0.4")
}
