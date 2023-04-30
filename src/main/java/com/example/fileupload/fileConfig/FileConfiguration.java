package com.example.fileupload.fileConfig;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class FileConfiguration implements WebMvcConfigurer {
    String filePath = "src/main/resources/images";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file : " + filePath);

    }
}
