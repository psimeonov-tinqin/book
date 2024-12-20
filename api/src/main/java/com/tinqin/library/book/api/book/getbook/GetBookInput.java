package com.tinqin.library.book.api.book.getbook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqin.library.book.api.base.ProcessorInput;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

import static com.tinqin.library.book.api.ValidationMessages.CLIENT_ID_CANNOT_BE_NULL;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class GetBookInput implements ProcessorInput {

    @UUID
    @NotBlank(message = CLIENT_ID_CANNOT_BE_NULL)
    private String bookId;

    private String locale;
}
