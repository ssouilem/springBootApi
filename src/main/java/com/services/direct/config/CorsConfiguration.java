package com.services.direct.config;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * Configures CORS globally.
 */

@Configuration
@EnableWebMvc // @TODO l'activation @EnableWebMvc bloque la page Swagger-ui.html
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
			
			// @TODO verifier l'utilisation de cette fonction
			@Override
		    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		        converters.add(new MappingJackson2HttpMessageConverter(
		                new Jackson2ObjectMapperBuilder()
		                        .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
		                        .dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
		                        .build()));
		    }
		};
	}

}
