package com.country.details.democountry.service;

import com.country.details.democountry.dto.CountryInfoDTO;
import com.country.details.democountry.modal.Country;
import com.country.details.democountry.repository.CountryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.country.details.democountry.util.Constant.COUNTRY_INFO_NAME;

/**
 * Business logic implementation for service class.
 *
 * @author kvasanthakumar
 * @version 0.0.1
 * Date: April 4,2024
 */
@Service
@Slf4j
public class CountryServiceImpl implements CountryService{

    @Value("${country.info.app.name}")
    private String applicationName;

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

    @Autowired
    WebClient webClient;


    /**
     * Description Retrieves all countries.
     *
     * @return List of retrieved countries, or empty list if not.
     */
    @Cacheable("allCountries")
    public List<Country> getCountries() {
        log.info("Calling get countries service level...");
        return countryRepository.findAll();
    }

    /**
     * Description : External api call to get country info for given country name.
     * @param countryName Name of the country.
     * @return CountryInfoDto
     */
    @CircuitBreaker(name = "country", fallbackMethod = "getCountryInfoByNameFallback")
    public CountryInfoDTO getCountryInfoByName(String countryName) throws JsonProcessingException {
        log.info("Calling get country by name service level...");
        ServiceInstance serviceInstance = loadBalancerClient.choose(applicationName);
        String baseUrl = serviceInstance.getUri().toString();
        String response = this.webClient
                .get()
                .uri(baseUrl + COUNTRY_INFO_NAME,countryName)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return getRequiredData(response);
    }

    /**
     * Description: Fallback function for external webservice call.
     * @return CountryInfoDTO
     */
    public CountryInfoDTO getCountryInfoByNameFallback(Exception exception) {
        log.info("Calling fallback...");
        return new CountryInfoDTO(null,null,null,0d,null,"County Info Service is currently not available. Please try again later");
    }

    /**
     * Description : Json to CountryInfoDTO mapping.
     * @return CountryInfoDTO
     */
    private CountryInfoDTO getRequiredData(String stringJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(stringJson, CountryInfoDTO.class);
    }

    /**
     * Description: Cache evict to getCountries method call.
     */
    @CacheEvict(value = "allCountries",allEntries = true)
    public void evictCache(){

    }
}
