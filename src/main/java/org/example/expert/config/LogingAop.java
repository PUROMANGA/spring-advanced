package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j

public class LogingAop {

    @Pointcut("execution(* org.example.expert.domain.comment.controller..*(..))")
    public void commentControllerMethod() {
    }

    @Pointcut("execution(* org.example.expert.domain.user.controller..*(..))")
    public void userControllerMethod() {
    }

    @Around("commentControllerMethod() || userControllerMethod()")
    public Object log(ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = null;
        HttpServletResponse response = null;

        Object[] args = joinPoint.getArgs();

        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                request = (HttpServletRequest) arg;
            } else if (arg instanceof HttpServletResponse) {
                response = (HttpServletResponse) arg;
            }
        }

        if(request != null && response != null) {

            ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper)(request);
            ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) (response);

            String userId = request.getParameter("id");
            Long requestTime = System.currentTimeMillis();
            String requestUrl = String.valueOf(request.getRequestURL());
            String requestBody = new String(requestWrapper.getContentAsByteArray());
            String responseBody = new String(responseWrapper.getContentAsByteArray());

            log.info("요청한 사용자의 ID = {}", userId);
            log.info("API 요청 시각 = {}", requestTime);
            log.info("API 요청 URL = {}", requestUrl);
            log.info("요청 본문(RequestBody) = {}", requestBody);
            log.info("응답 본문(ResponseBody) = {}", responseBody);
        }


        try {
            log.info("컨트롤러 실행 전");
            Object result = joinPoint.proceed();
            log.info("컨트롤러 실행 후");

            return result;
        } catch (Throwable e) {
            throw new RuntimeException("오류가 생겼습니다.", e);
        }
    }
}
