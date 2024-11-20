package com.tinqin.library.book.api.operations.getbook;

import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GetBookOutput implements ProcessorResult {

  private String author;
  private String title;
  private String pages;

}
