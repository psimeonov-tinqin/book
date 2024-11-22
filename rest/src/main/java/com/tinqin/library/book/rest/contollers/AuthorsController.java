package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.author.create.CreateAuthor;
import com.tinqin.library.book.api.author.create.CreateAuthorInput;
import com.tinqin.library.book.api.author.create.CreateAuthorOutput;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.tinqin.library.book.api.APIRoutes.API_AUTHOR;

@RestController
@RequiredArgsConstructor
public class AuthorsController {
    private final CreateAuthor createAuthor;


    @PostMapping(API_AUTHOR)
    public ResponseEntity<?> createAuthor(@RequestBody CreateAuthorInput input) {
        CreateAuthorOutput result = createAuthor.process(input);

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
