package com.tinqin.library.book.core.processors.book;

import com.tinqin.library.book.api.book.getbook.GetBook;
import com.tinqin.library.book.api.book.getbook.GetBookInput;
import com.tinqin.library.book.api.book.getbook.GetBookOutput;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.core.aspects.loggingaspect.LoggedProcessing;
import com.tinqin.library.book.core.aspects.loggingaspect.LoggingLevel;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tinqin.library.book.api.ValidationMessages.BOOK_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GetBookProcessor implements GetBook {

  private final BookRepository bookRepository;
  private final ErrorHandler errorHandler;

  @Override
  @LoggedProcessing(logLevel = LoggingLevel.DEBUG)
  public Either<OperationError, GetBookOutput> process(GetBookInput input) {
    return fetchBook(input)
        .map(this::convertGetBookInputToGetBookOutput)
        .toEither()
        .mapLeft(errorHandler::handle);
  }

  private Try<Book> fetchBook(GetBookInput input) {
    return Try.of(() -> bookRepository.findById(UUID.fromString(input.getBookId()))
        .orElseThrow(() -> new BusinessException(BOOK_NOT_FOUND)));
  }

  private GetBookOutput convertGetBookInputToGetBookOutput(Book book) {
    return GetBookOutput.builder()
        .author(String.format("%s %s", book.getAuthor().getFirstName(), book.getAuthor().getLastName()))
        .title(book.getTitle())
        .pages(book.getPages())
        .build();
  }

}
