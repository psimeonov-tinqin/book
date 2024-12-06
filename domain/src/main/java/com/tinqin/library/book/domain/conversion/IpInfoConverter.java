package com.tinqin.library.book.domain.conversion;


import java.util.Hashtable;

import org.springframework.stereotype.Component;

@Component
public class IpInfoConverter {

  public static final String IP_INFO_FAIL = "Failed to convert IP address";

  public Hashtable<String, String> convertIpInfoToMap(
      com.tinqin.library.book.domain.model.IpInfoResponse ipInfoResponse) {
    try {
      Hashtable<String, String> ipInfoMap = new Hashtable<>();
      ipInfoMap.put("IP Address", ipInfoResponse.getIp());
      ipInfoMap.put("Geolocation", ipInfoResponse.getLoc());
      ipInfoMap.put("City", ipInfoResponse.getCity());
      ipInfoMap.put("Country", ipInfoResponse.getCountry());
      ipInfoMap.put("ISP", ipInfoResponse.getOrg());
      return ipInfoMap;
    } catch (Exception e) {
      throw new RuntimeException(IP_INFO_FAIL, e);
    }
  }
}
