package org.example.infrastructure.config.filters;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.infrastructure.config.TokenJwtConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class JwtValidationFilter extends BasicAuthenticationFilter {
    public JwtValidationFilter (AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Gson gson=new Gson();
        String header= request.getHeader(TokenJwtConfig.AUTHORIZATION);
        if(Objects.isNull(header) || !header.startsWith(TokenJwtConfig.BEARER)){
            chain.doFilter(request,response);
            return;
        }
        String token=header.replace(TokenJwtConfig.BEARER,"");
        Claims claims = Jwts.parser().verifyWith(TokenJwtConfig.SECRET_KEY).build().parseSignedClaims(token).getPayload();
        String username= claims.getSubject();
        Object authoritiesClaims = claims.get("authorities");

        Type type = new TypeToken<List<SimpleGrantedAuthority>>(){}.getType();
        List<SimpleGrantedAuthority> roles = gson.fromJson(authoritiesClaims.toString(), type);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, roles);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);
    }
}
