package com.soyo.kurosaki.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/").setCachePeriod(0);
        registry.addResourceHandler("/plugin/**", "/static/**")
                .addResourceLocations("classpath:/plugin/", "classpath:/static/");
    }
}
