package com.tinqin.library.book.persistence.filereaderfactory.base;

import com.tinqin.library.book.persistence.filereaderfactory.models.BookModel;

import java.util.List;

public interface FileReader {

    List<BookModel> getBatch();
}
