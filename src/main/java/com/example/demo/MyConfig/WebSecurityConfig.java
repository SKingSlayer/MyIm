package com.example.demo.MyConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
public class WebSecurityConfig implements WebMvcConfigurer {

    //    @Bean
//    public SecurityInterceptor getSecurityInterceptor(){
//        return  new SecurityInterceptor();
//    }


//    @Override
//    public void addViewControllers( ViewControllerRegistry registry ) {
//        //默认到登陆页
//        registry.addViewController("/in").setViewName("/index");
//        registry.addViewController("/test").setViewName("/test1");
//    }
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**").addResourceLocations(
            "classpath:/static/");
    registry.addResourceHandler("swagger-ui.html").addResourceLocations(
            "classpath:/META-INF/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations(
            "classpath:/META-INF/resources/webjars/");
    WebMvcConfigurer.super.addResourceHandlers(registry);
}
//    @Override
//    public void addInterceptors(InterceptorRegistry registry){
////        InterceptorRegistration addInterceptor = registry.addInterceptor(getSecurityInterceptor());
//        //排除配置
//        addInterceptor.excludePathPatterns("/login");
//        addInterceptor.excludePathPatterns("/js/**");
//        addInterceptor.excludePathPatterns("/css/**");
//
//        //拦截配置
//        addInterceptor.addPathPatterns("/**");
//    }
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/cors/**").
            allowedHeaders("*").
            allowedMethods("*").
            maxAge(1800).
            allowedOrigins("*");

    registry.addMapping("/**").
            allowedHeaders("*").
            allowedMethods("*").
            maxAge(1800).
            allowedOrigins("http://localhost:63342");
}
    private static class SecurityInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, ServletException {
            HttpSession session = request.getSession();
            System.out.println(request.getRequestURI());
            return  true;
            //判断是否已有该用户登录的session
//            if(session.getAttribute("account") !=null){
//                );
//                return true;
//            }
//            //跳转到登录页
//            String url = "/login";
//            response.sendRedirect(url);
//            return false;
//        }
        }

}
}
