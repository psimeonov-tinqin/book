package com.tinqin.library.book.core.processors.book;

import com.tinqin.library.book.api.book.getbooksbyauthor.GetBookByAuthor;
import com.tinqin.library.book.api.book.getbooksbyauthor.GetBookByAuthorInput;
import com.tinqin.library.book.api.book.getbooksbyauthor.GetBookByAuthorOutput;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.models.BookModel;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.exceptions.BusinessException;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.models.Book;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import com.tinqin.library.book.persistence.repositories.BookRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class GetBookByAuthorProcessor implements GetBookByAuthor {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final ConversionService conversionService;
    private final ErrorHandler errorHandler;

    @Override
    public Either<OperationError, GetBookByAuthorOutput> process(GetBookByAuthorInput input) {
        return Try.of(() -> {

                    Author author = authorRepository
                            .findById(UUID.fromString(input.getAuthorId()))
                            .orElseThrow(() -> new BusinessException("Author not found"));

                    List<Book> pagedBooks = bookRepository
                            .findByAuthor(author, input.getPageable());

                    List<BookModel> bookModels = pagedBooks
                            .stream()
                            .map(bookEntity -> conversionService.convert(bookEntity, BookModel.class))
                            .toList();

                    return GetBookByAuthorOutput.builder()
                            .books(bookModels)
                            .build();
                })
                .toEither()
                .mapLeft(errorHandler::handle);
    }
}
