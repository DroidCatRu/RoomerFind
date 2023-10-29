package ru.droidcat.roomerfind.server.config

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.HoconApplicationConfig

class Configuration {

    private val config by lazy {
        HoconApplicationConfig(ConfigFactory.load())
    }

    fun fetchProperty(key: String) = config.property(key).getString()
}
