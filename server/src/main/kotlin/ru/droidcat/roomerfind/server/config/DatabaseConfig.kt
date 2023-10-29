package ru.droidcat.roomerfind.server.config

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

fun createDatabaseSource(
    config: Configuration,
): DataSource = HikariConfig().apply {
        config.run {
            isAutoCommit = true
            username = fetchProperty(USERNAME_KEY)
            password = fetchProperty(PASSWORD_KEY)
            jdbcUrl = fetchProperty(JDBC_URL_KEY)
            poolName = fetchProperty(POOL_NAME_KEY)
            minimumIdle = fetchProperty(MIN_IDLE_KEY).toInt()
            driverClassName = fetchProperty(DRIVER_CLASSNAME_KEY)
            maximumPoolSize = fetchProperty(MAX_POOL_SIZE_KEY).toInt()
            connectionTestQuery = fetchProperty(CONNECTION_TEST_QUERY_KEY)
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        }
        validate()
    }.let(::HikariDataSource)

fun createSqlDriver(dataSource: DataSource): SqlDriver = dataSource.asJdbcDriver().apply {
//    Database.Schema.migrate(driver, 1, 1)
}

private const val MIN_IDLE_KEY = "env.datasource.minIdle"
private const val JDBC_URL_KEY = "env.datasource.jdbcUrl"
private const val PASSWORD_KEY = "env.datasource.password"
private const val USERNAME_KEY = "env.datasource.username"
private const val POOL_NAME_KEY = "env.datasource.poolName"
private const val MAX_POOL_SIZE_KEY = "env.datasource.maxPoolSize"
private const val DRIVER_CLASSNAME_KEY = "env.datasource.driverClassName"
private const val CONNECTION_TEST_QUERY_KEY = "env.datasource.connectionTestQuery"
