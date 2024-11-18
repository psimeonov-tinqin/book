package com.tinqin.library.book.core.processors.hello;

import com.tinqin.library.book.api.hello.HelloWorld;
import com.tinqin.library.book.api.hello.HelloWorldInput;
import com.tinqin.library.book.api.hello.HelloWorldResult;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldOperation implements HelloWorld {
    @Override
    public HelloWorldResult process(HelloWorldInput input) {
        return HelloWorldResult
                .builder()
                .message("Hello from processor")
                .build();
    }
}
