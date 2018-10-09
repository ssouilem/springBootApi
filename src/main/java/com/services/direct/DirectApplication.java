package com.services.direct;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.services.direct")
@SpringBootApplication
public class DirectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DirectApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
//	@Bean
//	public ContactMapper contactMapper() {
//		ContactMapper INSTANCE = Mappers.getMapper( ContactMapper.class );
//		return INSTANCE;
//	}
	
	//For non Spring application, we can create instance by using new keyword
	// ModelMapper modelMapper = new ModelMapper();
}
