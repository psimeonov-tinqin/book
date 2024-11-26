package com.tinqin.library.book.core.processors;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.operations.getbook.GetBook;
import com.tinqin.library.book.api.operations.getbook.GetBookInput;
import com.tinqin.library.book.api.operations.getbook.GetBookOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class GetBookProcessor implements GetBook {

  private final BookRepository bookRepository;
  private final ErrorHandler errorHandler;


  @Override
  public Either<OperationError, GetBookOutput> process(GetBookInput input) {
    return Try.of(() -> result(input))
        .toEither()
        .mapLeft(errorHandler::handle);
  }

  private Book fetchBook(GetBookInput input) {
    return bookRepository.findById(UUID.fromString(input.getBookId()))
        .orElseThrow(()-> new RuntimeException("Book not found"));
  }

  private GetBookOutput convertGetBookInputToGetBookOutput(Book book) {
    return GetBookOutput.builder()
        .author(String.valueOf(book.getAuthor()))
        .title(book.getTitle())
        .pages(book.getPages())
        .build();
  }

  private GetBookOutput result(GetBookInput input) {
    Book book = fetchBook(input);
    return convertGetBookInputToGetBookOutput(book);
  }
}
