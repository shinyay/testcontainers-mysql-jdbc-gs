package com.google.shinyay.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class BookJdbcRepository(val jdbcTemplate: JdbcTemplate) {
}