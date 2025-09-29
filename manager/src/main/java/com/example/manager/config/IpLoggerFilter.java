package com.example.manager.config;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

// 在Filter或拦截器中设置IP 日志获取IP地址，并记录
@Configuration
public class IpLoggerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        String clientIp = request.getRemoteAddr();
        MDC.put("ip", clientIp); // 将IP存入MDC
        try {
            chain.doFilter(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            MDC.remove("ip"); // 确保请求结束后清除MDC，防止内存泄漏
        }
    }
}


