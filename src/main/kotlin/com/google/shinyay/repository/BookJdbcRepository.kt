package com.google.shinyay.repository

import com.google.shinyay.entity.Book
import com.google.shinyay.entity.BookRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class BookJdbcRepository(val jdbcTemplate: JdbcTemplate) {

    fun findAllBooks(): MutableList<Book> {
        return jdbcTemplate.query("select * from books", BookRowMapper())
    }
}