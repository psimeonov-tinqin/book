package com.tinqin.library.book.domain;

import com.tinqin.library.book.domain.model.IpInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ipInfoClient", url = "${ipinfo.url}")
public interface IpInfoFeignClient {

  @GetMapping(value = "/json", consumes = "application/json", produces = "application/json")
  IpInfoResponse getIpInfo();
}