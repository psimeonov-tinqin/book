package com.tinqin.library.book.api.postdemo;

import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostDemoResult implements ProcessorResult {
    private final String resultMessage;
}
