package org.example.infrastructure.config;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class TokenJwtConfig {

    private TokenJwtConfig(){}

    public static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String BEARER_FORMAT = BEARER+"%s";
}
