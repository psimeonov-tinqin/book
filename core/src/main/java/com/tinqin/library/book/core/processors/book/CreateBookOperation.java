package com.tinqin.library.book.core.processors.book;

import com.tinqin.library.book.api.book.create.CreateBook;
import com.tinqin.library.book.api.book.create.CreateBookInput;
import com.tinqin.library.book.api.book.create.CreateBookOutput;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateBookOperation implements CreateBook {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final ErrorHandler errorHandler;

  @Override
  public Either<OperationError, CreateBookOutput> process(CreateBookInput input) {

    return Try.of(() -> result(input))
        .toEither()
        .mapLeft(errorHandler::handle);
  }


  public CreateBookOutput result(CreateBookInput input) {

    Author author = authorRepository
        .findById(UUID.fromString(input.getAuthor()))
        .orElseThrow(() -> new BusinessException("Author not found"));

    Book book = Book
        .builder()
        .title(input.getTitle())
        .author(author)
        .pages(input.getPages())
        .price(BigDecimal.valueOf(Double.parseDouble(input.getPrice())))
        .createdAd(LocalDateTime.now())
        .stock(0)
        .isDeleted(false)
        .build();

    Book persisted = bookRepository.save(book);

    return CreateBookOutput
        .builder()
        .bookId(persisted.getId())
        .build();
  }
}


