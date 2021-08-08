package com.google.shinyay.unit

import com.google.shinyay.repository.BookJdbcRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import

@DataJdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(BookJdbcRepository::class)
class RepositoryTest {

    @Autowired
    lateinit var repository: BookJdbcRepository

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