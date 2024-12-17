package com.tinqin.library.book.rest.conf;

public class WhitelistedEndpoints {
    public static String[] WHITELISTED_ENDPOINTS = {
            "/swagger-ui/**",
            "/actuator/**",
            "/instances/**",
            "/swagger/**",
            "/v3/**",
            "/error",
    };
}
