package com.google.shinyay.unit

import com.google.shinyay.entity.Book
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
            {assertThat(result[0].title).isEqualTo("Spring")},
            {assertThat(result[1].title).isEqualTo("Java")},
            {assertThat(result[2].title).isEqualTo("GCP")}
        )
    }

    @Test
    fun findBookByIsbnShouldReturnBook() {
        val result1 = repository.findBookByIsbn("978-4-7710-1061-3")
        val result2 = repository.findBookByIsbn("978-4-7710-1062-3")
        val result3 = repository.findBookByIsbn("978-4-7710-1063-3")

        assertThat(result1[0].title).isEqualTo("Spring")
        assertThat(result2[0].title).isEqualTo("Java")
        assertThat(result3[0].title).isEqualTo("GCP")
    }

    @Test
    fun saveBookShouldReturnOne() {
        val book = Book(isbn = "978-4-7710-1061-1", title = "Persistence", author = "shinyay", price = 500)
        val result = repository.saveBook(book)

        assertThat(result).isEqualTo(1)
    }

    @Test
    fun saveBookAndFindBookByIsbnShouldReturnBook() {
        val book = Book(isbn = "978-4-7710-1061-2", title = "PersistenceAndFind", author = "shinyay", price = 550)
        repository.saveBook(book)
        val result = repository.findBookByIsbn("978-4-7710-1061-2")

        assertThat(result[0].title).isEqualTo("PersistenceAndFin")
    }
}