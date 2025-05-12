package com.example.loan.utils;


import com.example.loan.exception.CustomException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
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
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader("Authorization");

        try {
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                String username = jwtUtils.extractUsername(token); // may throw
                Long userId = jwtUtils.extractUserId(token);
                if (username != null && jwtUtils.validateToken(token)) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            username, null, List.of()
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    request.setAttribute("userId", userId);
                }
            }

            chain.doFilter(request, response); // continue
        } catch (CustomException e) {
            // send 401 manually
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ((jakarta.servlet.http.HttpServletResponse) response).setStatus(e.getStatus().value());
            response.getWriter().write("{\"statusCode\": " + e.getStatus().value() +
                    ", \"message\": \"" + e.getMessage() +
                    "\", \"data\": " + null + " }");
        }
     }
}
