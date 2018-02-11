package com.roncoo.education;

import com.roncoo.education.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zenngwei
 * @date 2018/02/09 17:13
 * @describe 增加静态资源存放位置，暂时没用
 */
@Configuration
public class MvcConfiguration  extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("webapp/**").addResourceLocations("classpath:/webapp/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
//        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("api/path/**").excludePathPatterns("api/path/login");
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");
    }
}
