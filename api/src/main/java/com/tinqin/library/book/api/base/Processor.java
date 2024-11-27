package com.tinqin.library.book.api.base;

import com.tinqin.library.book.api.errors.OperationError;
import io.vavr.control.Either;
import org.springframework.validation.Errors;

public interface Processor<R extends ProcessorResult, I extends ProcessorInput> {

    Either<OperationError, R> process(I input);
}
