package com.tinqin.library.book.domain;

import org.springframework.cloud.openfeign.FeignClient;

import com.tinqin.library.reporting.restexport.ReportingRestExport;

@FeignClient(name = "reporting", url = "${reporting.url}")
public interface ReportingRestClient extends ReportingRestExport {

}
