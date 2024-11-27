package com.tinqin.library.book.rest.contollers;

import com.tinqin.library.book.api.base.ProcessorResult;
import com.tinqin.library.book.api.errors.OperationError;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

  public BaseController() {

  }

  protected <O extends ProcessorResult> ResponseEntity<?> mapToResponseEntity(
      Either<OperationError, O> either, HttpStatus httpStatus) {

    return either.isRight() ? new ResponseEntity((ProcessorResult) either.get(), httpStatus)
        : new ResponseEntity(((OperationError) either.getLeft()).getStatus(), httpStatus);
  }

}
