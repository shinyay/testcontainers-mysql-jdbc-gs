package com.google.shinyay.entity

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet

class BookRowMapper : RowMapper<Book> {
    override fun mapRow(rs: ResultSet, rowNum: Int): Book? {
        return Book(
            rs.getLong("id"),
            rs.getString("isbn"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getLong("price")
        )
    }
}