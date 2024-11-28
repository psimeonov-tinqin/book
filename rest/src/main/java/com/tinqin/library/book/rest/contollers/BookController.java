package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.APIRoutes;
import com.tinqin.library.book.api.book.create.CreateBook;
import com.tinqin.library.book.api.book.create.CreateBookInput;
import com.tinqin.library.book.api.book.create.CreateBookOutput;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.book.getbook.GetBook;
import com.tinqin.library.book.api.book.getbook.GetBookInput;
import com.tinqin.library.book.api.book.getbook.GetBookOutput;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BookController extends BaseController {

  private final GetBook getBook;
  private final CreateBook createBook;

  @GetMapping(APIRoutes.GET_BOOK)
  public ResponseEntity<?> getBook(@PathVariable("bookId") String bookId) {


        GetBookInput input = GetBookInput
        .builder()
        .bookId(bookId)
        .build();

    Either<OperationError,GetBookOutput> result = getBook.process(input);

    return mapToResponseEntity(result,HttpStatus.OK);
  }

  @PostMapping(APIRoutes.API_BOOK)
    public ResponseEntity<?> createBook(@Valid @RequestBody CreateBookInput input) {

Either<OperationError,CreateBookOutput> result =createBook.process(input);

    return mapToResponseEntity(result,HttpStatus.CREATED);
    }

}
