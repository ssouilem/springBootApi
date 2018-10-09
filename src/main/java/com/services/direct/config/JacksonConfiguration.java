//package com.services.direct.config;
//
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
//
//@Configuration
//public class JacksonConfiguration {
//
//    private static final String SPRING_HATEOAS_OBJECT_MAPPER = "_halObjectMapper";
//
//    @Autowired
//    @Qualifier(SPRING_HATEOAS_OBJECT_MAPPER)
//    private ObjectMapper springHateoasObjectMapper;
//
//    @Primary
//    @Bean(name = "objectMapper")
//    ObjectMapper objectMapper() {
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(DateTimeFormatter.ISO_INSTANT));
//
//        springHateoasObjectMapper.registerModules(javaTimeModule);
//
//        springHateoasObjectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        springHateoasObjectMapper.disable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS);
//
//        springHateoasObjectMapper.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);
//        springHateoasObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//        springHateoasObjectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
//
//        springHateoasObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//        springHateoasObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        return springHateoasObjectMapper;
//    }
//}
