package com.services.direct.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@ComponentScan("com.services.direct.mapping")
@Configuration
public class AppConfig {

//  @Bean
//  public DataSource dataSource() {
//    return new EmbeddedDatabaseBuilder().addScript("schema.sql").build();
//  }
  
}