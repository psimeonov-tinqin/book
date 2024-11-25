package com.tinqin.library.book.core.errorhandler.components;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandlerComponent;
import org.springframework.stereotype.Component;

@Component
public class BusinessErrorHandlerComponent implements ErrorHandlerComponent {
    @Override
    public OperationError handle(Throwable throwable) {
        return null;
    }

    @Override
    public ErrorHandlerComponent getNext() {
        return null;
    }

    @Override
    public void setNext(ErrorHandlerComponent next) {

    }
}
