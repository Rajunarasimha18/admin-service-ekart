package com.raju.admin.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.raju.admin.appuser.AppUser;
import com.raju.admin.appuser.IAppUserDao;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
	private IAppUserDao iAppUserDetailsDao;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        //System.out.println("Authorization Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
        	//System.out.println("token from headers :: "+token);
            String username = jwtUtil.extractUsername(token);
            AppUser user = iAppUserDetailsDao.findAppUserByToken(token);
            
            if(!username.equalsIgnoreCase("admin@ekart.com")){
	            if (user == null) {
	                response.sendError(401, "Invalid token. Logged in from another device.");
	                return;
	            }
           
            	String storedToken = iAppUserDetailsDao.findAppUserByToken(token).getCurrentToken();
                System.out.println("Stored Token :: " + storedToken);

                if (!token.equals(storedToken)) {
                    response.sendError(401, "Invalid token. Logged in from another device.");
                    return;
                }
            }
            
            

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.emptyList()
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception e) {
            System.out.println("JWT error: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}