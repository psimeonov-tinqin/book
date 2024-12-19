package com.tinqin.library.book.core.processors.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.tinqin.library.book.api.book.partialedit.PartialEditBook;
import com.tinqin.library.book.api.book.partialedit.PartialEditBookInput;
import com.tinqin.library.book.api.book.partialedit.PartialEditBookOutput;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.models.PartialEditPojo;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PartialEditBookOperation implements PartialEditBook {
    private final BookRepository bookRepository;
    private final ErrorHandler errorHandler;
    private final ObjectMapper objectMapper;
    private final ConversionService conversionService;


    @Override
    public Either<OperationError, PartialEditBookOutput> process(PartialEditBookInput input) {

        return Try.of(() -> {
                    Book book = bookRepository
                            .findById(UUID.fromString(input.getBookId()))
                            .orElseThrow(() -> new RuntimeException("Book not found"));

                    PartialEditPojo convertedInput = conversionService.convert(input, PartialEditPojo.class);

                    JsonNode existingBook = objectMapper.valueToTree(book);
                    JsonNode fieldsToUpdate = objectMapper
                            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                            .valueToTree(convertedInput);

                    JsonMergePatch patch = JsonMergePatch.fromJson(fieldsToUpdate);
                    JsonNode patchedBook = patch.apply(existingBook);

                    Book updatedBook = objectMapper.treeToValue(patchedBook, Book.class);

                    Book persistedBook = bookRepository.save(updatedBook);

                    return PartialEditBookOutput
                            .builder()
                            .bookId(persistedBook.getId())
                            .build();
                })
                .toEither()
                .mapLeft(errorHandler::handle);
    }
}
