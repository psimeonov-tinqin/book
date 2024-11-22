package com.tinqin.library.book.core.processors.book;

import com.tinqin.library.book.api.book.create.CreateBook;
import com.tinqin.library.book.api.book.create.CreateBookInput;
import com.tinqin.library.book.api.book.create.CreateBookOutput;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateBookOperation implements CreateBook {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public CreateBookOutput process(CreateBookInput input) {
        Author author = authorRepository
                .findById(UUID.fromString(input.getAuthor()))
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));

        Book book = Book
                .builder()
                .title(input.getTitle())
                .author(author)
                .pages(input.getPages())
                .price(BigDecimal.valueOf(Double.parseDouble(input.getPrice())))
                .build();

        Book persisted = bookRepository.save(book);

        return CreateBookOutput
                .builder()
                .bookId(persisted.getId())
                .build();
    }
}
