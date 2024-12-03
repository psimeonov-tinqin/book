package com.tinqin.library.book.api.book.getbooksbyauthor;

import com.tinqin.library.book.api.base.ProcessorResult;
import com.tinqin.library.book.api.models.BookModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetBookByAuthorOutput implements ProcessorResult {

    private List<BookModel> books;

}
