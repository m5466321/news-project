package com.example.manager.aspect;

import com.example.manager.entity.OperateLog;
import com.example.manager.service.UserService;
import com.example.manager.utils.JwtUtil;
import com.example.manager.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Map;
/*
* AOP切面，用来记录日志，对应的@SystemLogger，自定义注解，用来获取操作内容
* */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggerAspect {

    private final UserService userService;

    @Around("@annotation(systemLogger)")
    public Object around(ProceedingJoinPoint joinPoint, SystemLogger systemLogger) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization");
        Map<String, Object> claims = JwtUtil.parseToken(token);
        Object result = joinPoint.proceed();
        OperateLog logger = new OperateLog();
        if (systemLogger != null) {
            logger.setOperateContent(systemLogger.value());
        }

        String username=(String) claims.get("username");
        logger.setOperateBy(username);
        logger.setOperateTime(LocalDateTime.now());
        userService.add(logger);
        if (systemLogger != null && systemLogger.consoleLog()) {
            log.info( username, systemLogger.value());
        }
        return result;
    }
}
