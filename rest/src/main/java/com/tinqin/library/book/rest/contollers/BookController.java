package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.APIRoutes;
import com.tinqin.library.book.api.operations.getbook.GetBook;
import com.tinqin.library.book.api.operations.getbook.GetBookInput;
import com.tinqin.library.book.api.operations.getbook.GetBookOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookController {

  private final GetBook getBook;

  @GetMapping(APIRoutes.GET_BOOK)
  public ResponseEntity<?> getBook(@PathVariable("bookId") String bookId) {

    GetBookInput input = GetBookInput
        .builder()
        .bookId(bookId)
        .build();

    GetBookOutput result = getBook.process(input);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
