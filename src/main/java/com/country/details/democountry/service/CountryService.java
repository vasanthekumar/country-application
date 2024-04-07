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

import java.util.List;

/**
 * Service class for managing entities of the type Country.
 *
 * This service provides business logic operation for managing entities of type Country.
 * It interacts with the CountryRepository to perform CRUD (Create,Read,Update,Delete) operations.
 *
 * @author kvasanthakumar
 * @version 0.0.1
 * Date: April 4,2024
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    /**
     * Properties injection to autowire CountryRepository dependency.
     */
    @Autowired
    CountryRepository countryRepository;

    /**
     * Properties injection to autowire LoadBalancerClient dependency.
     */
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Bean
    public WebClient webClient() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("country-info");
        String baseUrl = serviceInstance.getUri().toString();
        return WebClient.create(baseUrl);
    }

    /**
     * Retrives all countries.
     *
     * @return List of retrived countries, or empty list if not.
     */
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
