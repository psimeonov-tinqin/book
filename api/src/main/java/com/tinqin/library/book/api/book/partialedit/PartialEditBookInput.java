package com.tinqin.library.book.api.book.partialedit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqin.library.book.api.base.ProcessorInput;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PartialEditBookInput implements ProcessorInput {

    @UUID
    @Hidden
    @JsonIgnore
    private String bookId;

    @Length(max = 100)
    private String title;

    @Length(max = 100)
    private String pages;

    @Length(max = 100)
    private String price;
}
