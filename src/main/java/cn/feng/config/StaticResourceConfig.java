package cn.feng.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 静态资源解析
 */
@Configuration
@Slf4j
//@EnableWebMvc
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //自定义图片路径配置
        registry.addResourceHandler("/img/**").addResourceLocations("file:C:/public/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}