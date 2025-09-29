package com.example.manager.config;


import com.example.manager.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/Comments/addThumbsUp",
                "/Comments/addComments","/user/updateAvatar","/user/updateUserInfo","/user/userInfo");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /images/**是静态映射， file:/root/images/是文件在服务器的路径
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:E:\\Markdown\\news project\\manager\\src\\main\\resources\\static\\images");
    }

}

