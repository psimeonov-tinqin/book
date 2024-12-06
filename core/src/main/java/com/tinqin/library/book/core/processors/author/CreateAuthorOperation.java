package com.tinqin.library.book.core.processors.author;

import com.tinqin.library.book.api.author.create.CreateAuthor;
import com.tinqin.library.book.api.author.create.CreateAuthorInput;
import com.tinqin.library.book.api.author.create.CreateAuthorOutput;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.domain.ReportingRestClient;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.reporting.operations.createrecord.CreateRecordOutput;
import com.tinqin.library.reporting.operations.postevent.CreateEventInput;
import io.vavr.control.Either;
import io.vavr.control.Try;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAuthorOperation implements CreateAuthor {

  private final AuthorRepository authorRepository;
  private final ConversionService conversionService;
  private final ErrorHandler errorHandler;
  private final ReportingRestClient reportingRestClient;

  @Override
  public Either<OperationError, CreateAuthorOutput> process(CreateAuthorInput input) {
    return Try.of(() -> result(input))
        .toEither()
        .mapLeft(errorHandler::handle);
  }



  public CreateAuthorOutput result(CreateAuthorInput input) {
    Author author = conversionService.convert(input, Author.class);

    Author persisted = authorRepository.save(author);

    CreateRecordOutput createdRecord = createRecord();
    UUID recordId = createdRecord.getRecordId();


    CreateEventInput createEventInput = createEvent(recordId,"create author");
    reportingRestClient.createEvent(createEventInput);

    return CreateAuthorOutput
        .builder()
        .authorId(persisted.getId())
        .build();
  }
  private CreateEventInput createEvent(UUID recordId,String eventName) {
    return CreateEventInput
        .builder()
        .recordId(String.valueOf(recordId))
        .eventName(eventName)
        .build();
  }

private CreateRecordOutput createRecord() {
    return reportingRestClient.createRecord().getBody();
}
}
