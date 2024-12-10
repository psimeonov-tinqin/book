package com.tinqin.library.book.core.processors.book;

import static com.tinqin.library.book.api.ValidationMessages.BOOK_NOT_FOUND;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.book.getbook.GetBook;
import com.tinqin.library.book.api.book.getbook.GetBookInput;
import com.tinqin.library.book.api.book.getbook.GetBookOutput;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.exceptions.BusinessException;
import com.tinqin.library.book.domain.ReportingClient;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import com.tinqin.library.reporting.api.operations.createrecord.CreateRecordOutput;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetBookProcessor implements GetBook {

  private final BookRepository bookRepository;
  private final ErrorHandler errorHandler;
  private final ReportingClient reportingClient;

  @Override
  public Either<OperationError, GetBookOutput> process(GetBookInput input) {
    ResponseEntity<CreateRecordOutput> record = reportingClient.createRecord();
    HttpStatusCode statusCode = record.getStatusCode();

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
        .author(String.valueOf(book.getAuthor()))
        .title(book.getTitle())
        .pages(book.getPages())
        .build();
  }

}
