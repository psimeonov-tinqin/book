package com.tinqin.library.book.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IpInfoResponse {

  private String ip;
  private String loc;
  private String city;
  private String country;
  private String org;
}
