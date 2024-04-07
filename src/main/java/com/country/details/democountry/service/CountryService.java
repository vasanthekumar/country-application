package com.country.details.democountry.service;

import com.country.details.democountry.modal.Country;
import com.country.details.democountry.repository.CountryRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Bean
    public WebClient webClient() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("country-info");
        String baseUrl = serviceInstance.getUri().toString();
        return WebClient.create(baseUrl);
    }

    public List<Country> getCountry() {
        return countryRepository.findAll();
    }

    @CircuitBreaker(name = "country", fallbackMethod = "getCountryInfoByNamefallback")
    public String getCountryInfoByName(String countryName, String fields) {
//        ServiceInstance serviceInstance = loadBalancerClient.choose("country-info");
//        String baseUrl = serviceInstance.getUri().toString();
        return webClient()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/country/into/{name}")
                        .queryParam("fields", fields)
                        .build(countryName))
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public String getCountryInfoByNamefallback(Exception exception) {

        return new String("The country service is not available please try again later");
    }
}
