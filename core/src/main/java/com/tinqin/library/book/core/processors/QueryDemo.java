package com.tinqin.library.book.core.processors;

import com.tinqin.library.book.api.querydemo.QueryDemoInput;
import com.tinqin.library.book.api.querydemo.QueryDemoResult;
import org.springframework.stereotype.Service;

@Service
public class QueryDemo implements com.tinqin.library.book.api.querydemo.QueryDemo {
    @Override
    public QueryDemoResult process(QueryDemoInput input) {

        return QueryDemoResult
                .builder()
                .resultMessage("My query contains the following params: " + input.getQueryParam())
                .build();
    }
}
