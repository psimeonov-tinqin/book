package com.tinqin.library.book.core.processors.author;

import com.tinqin.library.book.api.author.create.CreateAuthor;
import com.tinqin.library.book.api.author.create.CreateAuthorInput;
import com.tinqin.library.book.api.author.create.CreateAuthorOutput;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.reporting.KafkaProducerService;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAuthorOperation implements CreateAuthor {

  private final AuthorRepository authorRepository;
  private final ConversionService conversionService;
  private final ErrorHandler errorHandler;
private final KafkaProducerService  producerService;



  @Override
  public Either<OperationError, CreateAuthorOutput> process(CreateAuthorInput input) {
    return
        persistAuthor(input)
            .toEither()
            .mapLeft(errorHandler::handle);
  }


  private Try<CreateAuthorOutput> persistAuthor(CreateAuthorInput input) {
    return Try.of(() -> {

      Author author = conversionService.convert(input, Author.class);

      Author persisted = authorRepository.save(author);

      producerService.createAuthorRecord(persisted.getId());

      return CreateAuthorOutput
          .builder()
          .authorId(persisted.getId())
          .build();
    });
  }


}
