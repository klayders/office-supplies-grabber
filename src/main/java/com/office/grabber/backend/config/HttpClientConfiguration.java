package com.office.grabber.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpClientConfiguration {

  @Bean
  RestTemplate restTemplate(ObjectMapper objectMapper){
    final var restTemplate = new RestTemplate();

    var converter = new MappingJackson2HttpMessageConverter();
    converter.setObjectMapper(objectMapper);

    restTemplate.getMessageConverters().add(converter);
    return restTemplate;
  }
}
