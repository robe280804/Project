package com.app_fitness.common_files.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class HeaderAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filter) throws ServletException, IOException {


        String userId = request.getHeader("X-User-Id");
        List<String> userRole = Collections.list(request.getHeaders("X-User-Role"));

        log.info("user id e  ruoli {} {}", userId, userRole);//ROLE_USER

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            List<SimpleGrantedAuthority> authorities = userRole != null
                    ? userRole.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .toList()
                    : Collections.EMPTY_LIST;

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId, null, authorities);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(auth);

        }

        filter.doFilter(request, response);
    }
}
