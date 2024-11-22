package com.tinqin.library.book.api.book.create;

import com.tinqin.library.book.api.base.ProcessorInput;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreateBookInput implements ProcessorInput {

    @NotBlank
    @Length(max = 100)
    private String title;

    @NotBlank
    @Length(max = 100)
    private String pages;

    @UUID
    private String author;

    @NotBlank
    @Length(max = 100)
    private String price;

}
