package com.example.pink_jelly.config.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PasswordConfirmationEntryPoint implements AuthenticationEntryPoint {
   // 특정 경로로 접근 시 비밀번호 확인 페이지로 이동
    private static final String[] PATHS_REQUIRING_PASSWORD_CONFIRMATION = {
            "/member/memberInfo",
            "/member/modifyPasswd"
    };

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        if (requiresPasswordConfirmation(requestURI)) {
            response.sendRedirect("/member/checkPW?target=" + requestURI);
        }
        else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean requiresPasswordConfirmation(String requestURI) {
        for (String path : PATHS_REQUIRING_PASSWORD_CONFIRMATION) {
            if (requestURI.startsWith(path)) {
                return true;
            }
        }
        return false;
    }
}
