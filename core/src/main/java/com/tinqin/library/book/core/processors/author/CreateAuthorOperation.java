package com.tinqin.library.book.core.processors.author;

import com.tinqin.library.book.api.author.create.CreateAuthor;
import com.tinqin.library.book.api.author.create.CreateAuthorInput;
import com.tinqin.library.book.api.author.create.CreateAuthorOutput;
import com.tinqin.library.book.persistence.models.Author;
import com.tinqin.library.book.persistence.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAuthorOperation implements CreateAuthor {
    private final AuthorRepository authorRepository;
    private final ConversionService conversionService;

    @Override
    public CreateAuthorOutput process(CreateAuthorInput input) {
        Author author = conversionService.convert(input, Author.class);

        Author persisted = authorRepository.save(author);

        return CreateAuthorOutput
                .builder()
                .authorId(persisted.getId())
                .build();
    }
}
