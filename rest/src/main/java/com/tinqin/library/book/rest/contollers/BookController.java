package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.APIRoutes;
import com.tinqin.library.book.api.author.create.CreateAuthor;
import com.tinqin.library.book.api.author.create.CreateAuthorInput;
import com.tinqin.library.book.api.author.create.CreateAuthorOutput;
import com.tinqin.library.book.api.book.create.CreateBook;
import com.tinqin.library.book.api.book.create.CreateBookInput;
import com.tinqin.library.book.api.book.create.CreateBookOutput;
import com.tinqin.library.book.api.operations.getbook.GetBook;
import com.tinqin.library.book.api.operations.getbook.GetBookInput;
import com.tinqin.library.book.api.operations.getbook.GetBookOutput;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class BookController {

  private final GetBook getBook;
  private final CreateBook createBook;

  @GetMapping(APIRoutes.GET_BOOK)
  public ResponseEntity<?> getBook(@PathVariable("bookId") String bookId) {

    GetBookInput input = GetBookInput
        .builder()
        .bookId(bookId)
        .build();

    GetBookOutput result = getBook.process(input);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping(APIRoutes.API_BOOK)
    public ResponseEntity<?> createBook(@Valid @RequestBody CreateBookInput input) {
    CreateBookOutput result = createBook.process(input);

    return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

}
