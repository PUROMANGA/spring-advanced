package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j

public class CustomInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object tokenUserRole = request.getAttribute("userRole");

        if (!UserRole.ADMIN.equals(tokenUserRole)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "관리자 권한이 없습니다.");
        }

        log.info("request.getSession().getCreationTime() = {}", request.getSession().getCreationTime());
        log.info("인증 성공시 요청한 URL = {}", request.getRequestURL());

        return true;
    }
}
