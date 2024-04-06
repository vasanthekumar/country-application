package com.country.details.democountry.util;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

public class Client {

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
