package com.projecttattoo.BrenoLendaTattoo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.micrometer.common.lang.NonNull;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer{
	@Override
	public void addCorsMappings(@SuppressWarnings("null") @NonNull CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("*")
		.allowedHeaders("*")
		.allowedMethods("GET", "POST", "PUT", "DELETE");
	}
}
