package org.example.infrastructure.config.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.infrastructure.config.TokenJwtConfig;
import org.example.infrastructure.exceptions.MalformedLoginException;
import org.example.infrastructure.security.entities.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.*;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.*;


public class JwtAuthFilter extends UsernamePasswordAuthenticationFilter {

    public static final String LOGIN_INCORRECTO = "Login incorrecto.";
    public static final String MALFORMED_LOGING = "Malformed loging.";
    public static final int EXPIRATION_TIME = 3600000;
    public static final String TOKEN = "token";
    public static final String USERNAME = "Username";
    public static final String AUTHORITIES = "authorities";
    public static final String MESSAGE = "message";

    private final AuthenticationManager authenticationManager;

    public JwtAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authenticationManager=authenticationManager;
        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setRedirectStrategy((request, response, url) -> {
        });
        this.setAuthenticationSuccessHandler(successHandler);
        this.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        });
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username=null;
        String password=null;
        UserEntity user;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(),UserEntity.class);
            username=user.getUsername();
            password=user.getPassword();
        } catch (IOException e) {
            throw new MalformedLoginException(MALFORMED_LOGING, e);
        }
        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(username,password);
        return this.authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Gson gson=new Gson();
        User user= (User) authResult.getPrincipal();
        String username=user.getUsername();
        Collection<? extends GrantedAuthority> roles=authResult.getAuthorities();
        Claims claims= Jwts.claims()
                .add(AUTHORITIES,gson.toJson(roles))
                .add(USERNAME,username)
                .build();
        String jwt = Jwts.builder()
                .subject(username)
                .claims(claims)
                .signWith(TokenJwtConfig.SECRET_KEY)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME)).compact();
        response.addHeader(TokenJwtConfig.AUTHORIZATION,String.format(TokenJwtConfig.BEARER_FORMAT,jwt));

        Map<String,String> body=new HashMap<>();
        body.put(TOKEN,jwt);
        body.put(USERNAME,username);

        response.getWriter().write(gson.toJson(body));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());

        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Gson gson=new Gson();
        Map<String,String> body=new HashMap<>();
        body.put(MESSAGE,LOGIN_INCORRECTO);

        response.getWriter().write(gson.toJson(body));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
