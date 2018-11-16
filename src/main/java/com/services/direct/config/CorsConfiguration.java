package com.services.direct.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configures CORS globally.
 */

@Configuration
@EnableWebMvc
public class CorsConfiguration {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*")
				.allowedMethods("*")
				.allowedHeaders("Access-Control-Allow-Origin", "*")
				.allowCredentials(false)
				.allowedHeaders("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
				.allowedHeaders("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with")
				.allowedHeaders("Authorization", "Cache-Control", "Content-Type")
				.allowedHeaders("Access-Control-Max-Age", "3600")
				.exposedHeaders(
						"Access-Control-Allow-Methods", 
						"Access-Control-Allow-Origin", 
						"Access-Control-Allow-Methods", 
						"Access-Control-Allow-Headers", 
						"Access-Control-Max-Age",
						"Access-Control-Request-Headers", 
						"Access-Control-Request-Method");
			}
		};
	}

}
