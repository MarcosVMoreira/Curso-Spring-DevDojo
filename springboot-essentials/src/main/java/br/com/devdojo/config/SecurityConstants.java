package br.com.devdojo.config;

import java.util.concurrent.TimeUnit;

public class SecurityConstants {
    // Authorization Bearer myTokenPasdkasdkoaspdapo

    public static final String SECRET = "DevDojo";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
    static final long EXPIRATION_TIME = 86400000l;


}
