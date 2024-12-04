package com.tinqin.library.book.api.book.getbooksbyauthor;

import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.domain.Pageable;


@Getter
@Builder
@AllArgsConstructor
@ToString
public class GetBookByAuthorInput implements ProcessorInput {

    @UUID
    @NotBlank
    private String authorId;

    Pageable pageable;

}
