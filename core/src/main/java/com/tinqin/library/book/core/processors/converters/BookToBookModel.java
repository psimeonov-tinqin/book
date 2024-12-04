package com.tinqin.library.book.core.processors.converters;

import com.tinqin.library.book.api.models.BookModel;
import com.tinqin.library.book.persistence.models.Book;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BookToBookModel implements Converter<Book, BookModel> {

    @Override
    public BookModel convert(Book source) {
        return BookModel
                .builder()
                .id(source.getId())
                .title(source.getTitle())
                .pages(Integer.valueOf(source.getPages()))
                .price(source.getPrice().doubleValue())
                .createdAt(source.getCreatedAd())
                .stock(source.getStock())
                .build();
    }
}
