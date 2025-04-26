package com.example.loan.utils;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends GenericFilter {

    @Autowired private JwtUtils jwtUtils;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer")) {
            String token = header.substring(7);
            String username = jwtUtils.extractUsername(token);

            if (username != null && jwtUtils.validateToken(token)) {
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        username, null, List.of()
                );
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(request, response);
     }
}
