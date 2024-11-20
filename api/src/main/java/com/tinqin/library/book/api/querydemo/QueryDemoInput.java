package com.tinqin.library.book.api.querydemo;

import com.tinqin.library.book.api.base.ProcessorInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class QueryDemoInput implements ProcessorInput {

    private String queryParam;
    private String queryParam2;

}
