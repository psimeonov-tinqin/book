package com.tinqin.library.book.core.processors.converters;

import com.tinqin.library.book.api.author.create.CreateAuthorInput;
import com.tinqin.library.book.persistence.models.Author;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CreateAuthorInputToAuthor implements Converter<CreateAuthorInput, Author> {

    @Override
    public Author convert(CreateAuthorInput source) {
        return Author
                .builder()
                .firstName(source.getFirstName())
                .lastName(source.getLastName())
                .build();
    }
}
