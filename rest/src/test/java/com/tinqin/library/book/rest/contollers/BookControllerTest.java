package com.tinqin.library.book.rest.contollers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqin.library.book.api.book.create.CreateBookInput;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.tinqin.library.book.api.APIRoutes.API_BOOK;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@ActiveProfiles("test")
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @SpyBean
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    void returns201whenSuccessful() {

        Author author = Author
                .builder()
                .firstName("Ivan")
                .lastName("Vazov")
                .build();

        authorRepository.save(author);

        CreateBookInput book = CreateBookInput
                .builder()
                .author(author.getId().toString())
                .title("Pod Igoto")
                .pages("200")
                .price("20.00")
                .build();

        when(objectMapper.writeValueAsString(book)).thenReturn("book");

        mockMvc.perform(post(API_BOOK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId").value(bookRepository.findAll().getFirst().getId().toString()));

        System.out.println();
    }

    @Test
    @SneakyThrows
    void returns201whenSuccessful2() {

        Author author = Author
                .builder()
                .firstName("Ivan")
                .lastName("Vazov")
                .build();

        authorRepository.save(author);

        CreateBookInput book = CreateBookInput
                .builder()
                .author(author.getId().toString())
                .title("Pod Igoto")
                .pages("200")
                .price("20.00")
                .build();

        mockMvc.perform(post(API_BOOK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookId").value(bookRepository.findAll().getFirst().getId().toString()));

        System.out.println();
    }
}