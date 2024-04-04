package com.country.details.democountry.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Client {
    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("http://localhost:8090/country").build();
    }
}
