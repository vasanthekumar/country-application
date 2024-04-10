package com.country.details.democountry.service;

import com.country.details.democountry.dto.CountryInfoDTO;
import com.country.details.democountry.modal.Country;
import com.country.details.democountry.repository.CountryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * Service class for managing entities of the type Country.
 * This service provides business logic operation for managing entities of type Country.
 * It interacts with the CountryRepository to perform CRUD (Create,Read,Update,Delete) operations.
 *
 * @author kvasanthakumar
 * @version 0.0.1
 * Date: April 4,2024
 */
@Service
public class CountryServiceImpl implements CountryService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryServiceImpl.class);

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
        return WebClient.create();
    }

    /**
     * Retrieves all countries.
     *
     * @return List of retrieved countries, or empty list if not.
     */
    @Cacheable("allCountries")
    public List<Country> getCountries() {
        return countryRepository.findAll();
    }

    @CircuitBreaker(name = "country", fallbackMethod = "getCountryInfoByNamefallback")
    public CountryInfoDTO getCountryInfoByName(String countryName) throws JsonProcessingException {
        ServiceInstance serviceInstance = loadBalancerClient.choose("country-info");
        String baseUrl = serviceInstance.getUri().toString();
        String response = webClient()
                .get()
                .uri(baseUrl + "/country/info/{name}",countryName)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return getRequiredData(response);
    }

    public CountryInfoDTO getCountryInfoByNamefallback(Exception exception) {
        return new CountryInfoDTO(null,null,null,0d,null,"County Info Service is currently not available. Please try again later");
    }

    private CountryInfoDTO getRequiredData(String result) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(result, CountryInfoDTO.class);
    }

    @CacheEvict(value = "allCountries",allEntries = true)
    public void evictCache(){

    }
}
