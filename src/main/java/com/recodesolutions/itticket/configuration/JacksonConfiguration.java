//package com.recodesolutions.itticket.configuration;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JacksonConfiguration {
//
//    @Bean
//    public ObjectMapper objectMapper() {
//        return new ObjectMapper()
//                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT ).registerModule(new JavaTimeModule());
//    }
//}