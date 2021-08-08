package com.google.shinyay.unit

import com.google.shinyay.repository.BookJdbcRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoryTest {

    @Autowired
    lateinit var repository: BookJdbcRepository

    companion object {
        @Container
        var databaseContainer = MySQLContainer<Nothing>(DockerImageName.parse("mysql:5.7.33")).apply {
            withDatabaseName("test")
            withUsername("user")
            withPassword("pwd")
        }


        @DynamicPropertySource
        @JvmStatic
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", databaseContainer::getJdbcUrl)
            registry.add("spring.datasource.username", databaseContainer::getUsername)
            registry.add("spring.datasource.password", databaseContainer::getPassword)
        }

//        fun datasource(): DataSource {
//            val datasource = MysqlDataSource()
//            datasource.setUrl(database.jdbcUrl)
//            datasource.user = database.username
//            datasource.password = database.password
//        }
    }

    @Test
    fun findAllBooksShouldReturnCount() {
        val result = repository.findAllBooks()
        assertThat(result.count()).isEqualTo(3)
    }

    @Test
    fun findAllBooksShouldReturnAllBooks() {
        val result = repository.findAllBooks()

        assertAll(
            {assertThat(result[0].title).isEqualTo("GCP")},
            {assertThat(result[1].title).isEqualTo("Spring")},
            {assertThat(result[2].title).isEqualTo("Java")}
        )

//        assertThat(result[0].title).isEqualTo("Spring")
//        assertThat(result[1].title).isEqualTo("Java")
//        assertThat(result[2].title).isEqualTo("GCP")
    }
}