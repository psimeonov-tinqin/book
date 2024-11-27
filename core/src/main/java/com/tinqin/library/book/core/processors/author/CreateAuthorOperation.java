package com.tinqin.library.book.core.processors.author;

import com.tinqin.library.book.api.author.create.CreateAuthor;
import com.tinqin.library.book.api.author.create.CreateAuthorInput;
import com.tinqin.library.book.api.author.create.CreateAuthorOutput;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class CreateAuthorOperation implements CreateAuthor {
    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;


    @Override
    public Either<OperationError, CreateAuthorOutput> process(CreateAuthorInput input) {
        return Try.of(() -> result(input))
            .toEither()
            .mapLeft(errorHandler::handle);
    }


    public CreateAuthorOutput result(CreateAuthorInput input) {
        Author author = conversionService.convert(input, Author.class);

        Author persisted = authorRepository.save(author);

        return CreateAuthorOutput
                .builder()
                .authorId(persisted.getId())
                .build();
    }


}
