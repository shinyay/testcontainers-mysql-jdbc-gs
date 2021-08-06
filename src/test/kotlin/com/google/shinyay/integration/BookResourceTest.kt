package com.google.shinyay.integration

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.shinyay.entity.Book
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureMockMvc
class BookResourceTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun shouldCreateBook() {
        val json = objectMapper.writeValueAsString(
            Book(id = 0, isbn = "978-4-7710-1067-3",title = "test", author = "shinyay", price = 100)
        )
        mockMvc.perform(post("/api/v1/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated)
        mockMvc.perform(get("/api/v1/books/978-4-7710-1067-3"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].title").exists())
            .andExpect(jsonPath("$[0].title").value("test"))

        val result = mockMvc.perform(get("/api/v1/books/978-4-7710-1067-3"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk)
            .andReturn()
        val javaType = objectMapper.typeFactory.constructCollectionType(List::class.java, Book::class.java)
        val bookObject: List<Book> = objectMapper.readValue(result.response.contentAsString, javaType)
        assertThat(bookObject[0].price).isEqualTo(100)

    }
}