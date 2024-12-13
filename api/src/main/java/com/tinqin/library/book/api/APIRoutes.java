package com.tinqin.library.book.api;

public class APIRoutes {

  public static final String API = "/api/v1";
  public static final String API_BOOK = API + "/book";
  public static final String GET_BOOK = API_BOOK + "/{bookId}";

  public static final String API_AUTHOR = API + "/author";

  public static final String API_USER = API + "/user";
  public static final String BLOCK_USER = API_USER + "/{userId}";

    public static final String AUTH = API + "/auth";
    public static final String LOGIN = AUTH + "/login";
    public static final String REGISTER = AUTH + "/register";
}
