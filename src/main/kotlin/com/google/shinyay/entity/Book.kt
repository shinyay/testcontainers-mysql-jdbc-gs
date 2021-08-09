package com.google.shinyay.entity

data class Book(
    var id: Long = 0,
    var isbn: String,
    var title: String,
    var author: String,
    var price: Long
)