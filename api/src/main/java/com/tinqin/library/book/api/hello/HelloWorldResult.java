package com.tinqin.library.book.api.hello;

import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class HelloWorldResult implements ProcessorResult {
    private final String message;
}
