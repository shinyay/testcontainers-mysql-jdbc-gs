package com.google.shinyay.service

import com.google.shinyay.entity.Book
import com.google.shinyay.repository.BookJdbcRepository
import org.springframework.stereotype.Service

@Service
class BookService(val repository: BookJdbcRepository) {

    fun findAllBooks(): MutableList<Book> {
        return repository.findAllBooks()
    }

//    fun findBookByIsbn(isbn: String): MutableList<Book> {
//        logger.info("[ISBN]: $isbn")
//        return repository.findBookByIsbn(isbn)
//    }
}