package com.tinqin.library.book.core.processors.book;

import static com.tinqin.library.book.api.ValidationMessages.AUTHOR_NOT_FOUND;

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
public class CreateBookProcessor implements CreateBook {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final ErrorHandler errorHandler;

  @Override
  public Either<OperationError, CreateBookOutput> process(CreateBookInput input) {
    return getAuthor(input)
        .flatMap(author -> createBook(input, author))
        .flatMap(this::saveBook)
        .toEither()
        .mapLeft(errorHandler::handle);
  }

  private Try<Author> getAuthor(CreateBookInput input) {
    return Try.of(() -> UUID.fromString(input.getAuthor()))
        .flatMap(authorId -> Try.of(() -> authorRepository.findById(authorId)
            .orElseThrow(() -> new BusinessException(AUTHOR_NOT_FOUND))));
  }

  private Try<Book> createBook(CreateBookInput input, Author author) {
    return Try.of(() -> Book.builder()
        .title(input.getTitle())
        .author(author)
        .pages(input.getPages())
        .price(BigDecimal.valueOf(Double.parseDouble(input.getPrice())))
        .createdAd(LocalDateTime.now())
        .stock(0)
        .isDeleted(false)
        .build());
  }

  private Try<CreateBookOutput> saveBook(Book book) {
    return Try.of(() -> bookRepository.save(book))
        .map(savedBook -> CreateBookOutput.builder()
            .bookId(savedBook.getId())
            .build());
  }


}


