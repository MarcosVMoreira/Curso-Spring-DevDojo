package br.com.devdojo.config;

import br.com.devdojo.service.CustomUserDetailsService;
import com.sun.org.apache.bcel.internal.generic.ARETURN;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static br.com.devdojo.config.SecurityConstants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final CustomUserDetailsService customUserDetailsService;

    public JWTAuthorizationFilter (AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
        super(authenticationManager);
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }


        System.out.println("autentication token JWTAuthorizationFilter"+getAuthenticationToken(request));


        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);


        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken (HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token == null) {
            return null;
        }

        String username = Jwts
                .parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        return username != null ? new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()) : null;
    }

}
