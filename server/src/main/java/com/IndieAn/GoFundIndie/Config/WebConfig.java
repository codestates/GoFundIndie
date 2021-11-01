package com.IndieAn.GoFundIndie.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("#{info['gofund.url1']}")
    private String url1;

    @Value("#{info['gofund.url2']}")
    private String url2;

    @Value("#{info['gofund.url3']}")
    private String url3;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(url1, url2, url3)
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "DELETE", "PATCH", "PUT")
                .allowCredentials(true);
    }
}
