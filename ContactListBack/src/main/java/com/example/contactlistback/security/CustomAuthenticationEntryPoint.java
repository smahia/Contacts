package com.example.contactlistback.security;

import com.example.contactlistback.error.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private final ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.addHeader("WWW-Authenticate", "Basic realm='" + getRealmName() + "'");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, authException.getMessage());

        // Transform the Api Error into a String
        String strApiError = mapper.writeValueAsString(apiError);

        PrintWriter writer = response.getWriter();
        writer.println(strApiError);
    }

    @PostConstruct
    public void initRealname() {
        setRealmName("contactList");
    }
}
