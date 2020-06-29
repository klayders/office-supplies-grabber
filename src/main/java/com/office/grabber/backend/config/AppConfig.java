package com.office.grabber.backend.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.TimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class AppConfig {

  @Bean
  ObjectMapper objectMapper() {
    var objectMapper = new ObjectMapper();
    var javaTimeModule = new JavaTimeModule();
    objectMapper.registerModule(javaTimeModule);
    objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
    objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    objectMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.setTimeZone(TimeZone.getDefault());
    return objectMapper;
  }
}
