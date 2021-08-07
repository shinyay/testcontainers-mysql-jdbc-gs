package com.google.shinyay.unit

import com.google.shinyay.repository.BookJdbcRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.testcontainers.junit.jupiter.Testcontainers
import javax.sql.DataSource

@Testcontainers
@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoryTest {

    @Autowired
    lateinit var repository: BookJdbcRepository

    @Autowired
    lateinit var dataSource: DataSource

//    companion object {
//        @Container
//        val database = MySQLContainer<Nothing>("mysql:5.7.33")
//
//        fun datasource(): DataSource {
//            val datasource = MysqlDataSource()
//            datasource.setUrl(database.jdbcUrl)
//            datasource.user = database.username
//            datasource.password = database.password
//        }
//    }
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