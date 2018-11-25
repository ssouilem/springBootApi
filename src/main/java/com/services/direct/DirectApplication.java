package com.services.direct;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.services.direct.bean.Bordereau;
import com.services.direct.bean.Customer;
import com.services.direct.repo.CustomerRepository;

@ComponentScan({"com.services.direct"})
@SpringBootApplication
@EnableConfigurationProperties
@EnableTransactionManagement
public class DirectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DirectApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
	@Configuration
    protected static class RepositoryMvcConfiguration extends RepositoryRestConfigurerAdapter {
        @Override
        public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
            config.exposeIdsFor(Bordereau.class, Customer.class);
            config.withEntityLookup().//
    		forRepository(CustomerRepository.class, Customer::getId, CustomerRepository::findById);
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        }
    }
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
	   return new WebMvcConfigurer() {
		   
	      @Override
	      public void addCorsMappings(CorsRegistry registry) {
	         registry.addMapping("/**").allowedOrigins("*");
	      }    
	   };
	}

}
