package com.crevainera.weby.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfiguration implements WebMvcConfigurer {

    public static final String PATH_PATTERN = "/**";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(PATH_PATTERN);
    }
}
