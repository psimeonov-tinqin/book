package com.tinqin.library.book.api.book.partialedit;

import com.tinqin.library.book.api.base.ProcessorResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PartialEditBookOutput implements ProcessorResult {

  private UUID bookId;

}
