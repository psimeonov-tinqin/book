package com.tinqin.library.book.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PartialEditPojo {

    private String bookId;
    private String title;
    private String pages;
    private String price;
}
