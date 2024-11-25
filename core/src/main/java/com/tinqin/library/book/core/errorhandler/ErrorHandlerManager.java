package com.tinqin.library.book.core.errorhandler;

import com.tinqin.library.book.api.errors.OperationError;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandler;
import com.tinqin.library.book.core.errorhandler.base.ErrorHandlerComponent;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ErrorHandlerManager implements ErrorHandler {
    private final ApplicationContext applicationContext;
    private List<ErrorHandlerComponent> components;

    @PostConstruct
    public void init() {
        components = applicationContext
                .getBeansOfType(ErrorHandlerComponent.class)
                .values()
                .stream()
                .toList();
    }

    @Override
    public OperationError handle(Throwable throwable) {
        return null;
    }
}
