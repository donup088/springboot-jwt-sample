package com.token.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.token.exception.ApiError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        ApiError apiError=new ApiError(HttpStatus.UNAUTHORIZED,"토큰이 잘못되었습니다.","Wrong Token");
        //TODO 토큰 만료 , 토큰 유효성 등등 에러에 따라 반환값이 다르도록
        response.getWriter().write(objectMapper.writeValueAsString(apiError));
    }
}