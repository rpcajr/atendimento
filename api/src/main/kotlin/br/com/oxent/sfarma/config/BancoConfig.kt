package br.com.oxent.sfarma.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:oxente.properties")
class BancoConfig @Autowired constructor(environment: Environment) {
    val env: Environment = environment

    @Bean
    fun getDataSource(): DataSource {
        return HikariDataSource(getHikariConfig())
    }

    private fun getHikariConfig(): HikariConfig {
        val hikariConfig = HikariConfig()
        hikariConfig.driverClassName = env.getProperty("driver")
        hikariConfig.username = env.getProperty("user")
        hikariConfig.password = env.getProperty("pass")
        hikariConfig.jdbcUrl = env.getProperty("url")
        val nPool = Integer.parseInt(env.getProperty("maximumPoolSize", "5"))
        hikariConfig.maximumPoolSize = nPool
        return hikariConfig
    }

    @Bean
    fun jdbcTemplate(): JdbcTemplate {
        return JdbcTemplate(getDataSource())
    }

    @Bean(name = ["transactionManager"])
    fun txManager(): PlatformTransactionManager {
        return DataSourceTransactionManager(getDataSource())
    }

}