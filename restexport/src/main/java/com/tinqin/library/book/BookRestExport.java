package com.tinqin.library.book;

import static com.tinqin.library.book.api.APIRoutes.API_AUTHOR;
import static com.tinqin.library.book.api.APIRoutes.API_BOOK;
import static com.tinqin.library.book.api.APIRoutes.BLOCK_USER;
import static com.tinqin.library.book.api.APIRoutes.GET_BOOK;

import com.tinqin.library.book.api.author.create.CreateAuthorInput;
import com.tinqin.library.book.api.author.create.CreateAuthorOutput;
import com.tinqin.library.book.api.book.create.CreateBookInput;
import com.tinqin.library.book.api.book.create.CreateBookOutput;
import com.tinqin.library.book.api.book.getbook.GetBookOutput;
import com.tinqin.library.book.api.book.getbooksbyauthor.GetBookByAuthorOutput;
import com.tinqin.library.book.api.user.BlockUserOutput;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "book")
public interface BookRestExport {

  @GetMapping(path = GET_BOOK, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<GetBookOutput> getBook(@PathVariable("bookId") String bookId);

  @GetMapping(path = API_BOOK, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<GetBookByAuthorOutput> getBooksByAuthor(@RequestParam("authorId") String authorId);

  @PostMapping(path = API_BOOK, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<CreateBookOutput> createBook(@Valid @RequestBody CreateBookInput input);

  @PostMapping(path = API_AUTHOR,produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<CreateAuthorOutput> createAuthor(@RequestBody CreateAuthorInput input);

  @PutMapping(path = BLOCK_USER, produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<BlockUserOutput> blockUser(@PathVariable("userId") String userId);
}
