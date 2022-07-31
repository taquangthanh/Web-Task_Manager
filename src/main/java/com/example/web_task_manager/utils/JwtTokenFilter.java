package com.example.web_task_manager.utils;

import com.example.web_task_manager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!hasAuthorizationHeader(request)){
            filterChain.doFilter(request,response);
            return;
        }

        String accessToken = getAccessToken(request);
        if(!jwtUtils.validateAccessToken(accessToken)){
            filterChain.doFilter(request,response);
            return;
        }
        setAuthenticationContext(accessToken,request);
        System.out.println("Access Token : "+ accessToken);
        filterChain.doFilter(request,response);
    }

    private void setAuthenticationContext(String accessToken, HttpServletRequest request) {
        UserDetails userDetails = getUserDetails(accessToken);
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(userDetails,null,null);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private UserDetails getUserDetails(String accessToken) {
        User userDetail = new User();
        String[] subjectArray = jwtUtils.getSubject(accessToken).split(",");
        userDetail.setId(Long.parseLong(subjectArray[0]));
        userDetail.setUsername(subjectArray[1]);
        return userDetail;
    }

    private boolean hasAuthorizationHeader(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        System.out.println("Authorization header: "+ header);
        if(ObjectUtils.isEmpty(header) || !header.startsWith("Bearer")){
            return false;
        }
         return true;
    }
    private String getAccessToken(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        System.out.println("Authorization header: "+ header);
        String token = header.split("")[1].trim();
        return token;
    }
}
