package com.country.details.democountry.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class Client {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Bean
    public WebClient webClient() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("country-info");
        String uri = serviceInstance.getUri().toString();
        return WebClient.builder().baseUrl(uri).build();
    }
}
