package com.services.direct.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("!test")
public class SwaggerConfig extends WebMvcConfigurationSupport {

    /**
     * Api.
     *
     * @return the docket
     */
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)  
          .select()                                  
          //.apis(RequestHandlerSelectors.any())
          .apis(RequestHandlerSelectors.basePackage("com.services.direct.api"))
          .paths(PathSelectors.ant("/**"))                          
          .build()
          .apiInfo(apiInfo());
    }

       /**
       * Api info.
       *
       * @return the api info
       */
       private ApiInfo  apiInfo() {
             return new ApiInfoBuilder()
                .title("Refonte acquisition application")
                .description("Expose Rest service by swagger")
                .contact(new Contact("Samir SOUILEM", null , "samir.souilem@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
                .version("1.0.0")
                .build();
       }
       
       @Override
       protected void addResourceHandlers(ResourceHandlerRegistry registry) {
           registry.addResourceHandler("swagger-ui.html")
                   .addResourceLocations("classpath:/META-INF/resources/");

           registry.addResourceHandler("/webjars/**")
                   .addResourceLocations("classpath:/META-INF/resources/webjars/");
       }
}
