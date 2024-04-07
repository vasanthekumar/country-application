package com.country.details.democountry.service;

import com.country.details.democountry.modal.Country;
import com.country.details.democountry.repository.CountryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
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
    public Country getCountryInfoByName(String countryName, String fields) throws JsonProcessingException {
//        ServiceInstance serviceInstance = loadBalancerClient.choose("country-info");
//        String baseUrl = serviceInstance.getUri().toString();
        String response = webClient()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/country/info/{name}")
                        .queryParam("fields", fields)
                        .build(countryName))
                .retrieve()
                .bodyToMono(String.class).block();
        return getRequiredData(response);
    }

    public String getCountryInfoByNamefallback(Exception exception) {
        return new String("The country service is not available please try again later");
    }

    private Country getRequiredData(String result) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(result);
        JsonNode data = objectMapper.readTree(jsonNode.get("data").asText()).get(0);
        Country country = new Country();
        country.setName(data.get("name").get("common").asText());
        country.setFlagFileUrl(data.get("flags").get("svg").asText());
        country.setCapital(data.get("capital").get(0).asText());
        country.setCountryCode(data.get("cca2").asText());
        country.setPopulation(data.get("population").asDouble());
        return country;

    }
}
