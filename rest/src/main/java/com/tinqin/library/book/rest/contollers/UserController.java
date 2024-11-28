package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.APIRoutes;
import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.api.user.BlockUser;
import com.tinqin.library.book.api.user.BlockUserInput;
import com.tinqin.library.book.api.user.BlockUserOutput;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController extends BaseController {

  private final BlockUser blockUser;

  @PutMapping(APIRoutes.BLOCK_USER)
  public ResponseEntity<?> blockUser(@PathVariable("userId") String userId) {
    BlockUserInput input = BlockUserInput
        .builder()
        .id(userId)
        .build();

    Either<OperationError, BlockUserOutput> result = blockUser.process(input);

    return mapToResponseEntity(result, HttpStatus.OK);

  }


}
