//package com.services.direct.config;
//
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//
//@Configuration
//@EnableWebMvc
//@ComponentScan(basePackages  = { "com.services.direct.api" })
//public class AppConfig  implements WebMvcConfigurer  {
//
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/swagger-ui.html")
//		.addResourceLocations("classpath:/META-INF/resources/");		
//		registry.addResourceHandler("/webjars/**")
//		.addResourceLocations("classpath:/META-INF/resources/webjars/");
//	}
//	
//	@Override 
//	public void addViewControllers(ViewControllerRegistry registry) { 
//		WebMvcConfigurer.super.addViewControllers(registry); 
//		registry.addViewController("/").setViewName("home"); 
//	} 
//}