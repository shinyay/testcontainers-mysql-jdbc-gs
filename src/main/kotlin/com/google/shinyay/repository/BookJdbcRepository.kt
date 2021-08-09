package com.google.shinyay.repository

import com.google.shinyay.entity.Book
import com.google.shinyay.entity.BookRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class BookJdbcRepository(val jdbcTemplate: JdbcTemplate) {

    fun findAllBooks(): MutableList<Book> {
        return jdbcTemplate.query("select * from books", BookRowMapper())
    }

    fun findBookByIsbn(isbn: String): MutableList<Book> {
        return jdbcTemplate.query("select * from books where isbn = ?", BookRowMapper(), isbn)
    }

    fun saveBook(book: Book): Int {
        return jdbcTemplate.update("insert into books (isbn, title, author, price) values (?,?,?,?)",
            book.isbn, book.title, book.author, book.price)
    }

    fun deleteByIsbn(isbn: String): Int {
        return jdbcTemplate.update("delete * from books where isbn = ?", isbn)
    }
}