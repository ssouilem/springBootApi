package com.services.direct.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages  = { "com.services.direct.api" })
public class WebConfig implements WebMvcConfigurer {


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/swagger-ui.html")
		.addResourceLocations("classpath:/META-INF/resources/");		
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations("classpath:/META-INF/resources/webjars/");
		registry.addResourceHandler("/resources/**")
        .addResourceLocations("(/resources/");
		
	}
	
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
//	@Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(new MappingJackson2HttpMessageConverter(
//                new Jackson2ObjectMapperBuilder()
//                        .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
//                        .dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
//                        .build()));
//    }
			

}