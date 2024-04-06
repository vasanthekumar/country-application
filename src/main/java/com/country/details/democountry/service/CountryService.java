package com.country.details.democountry.service;

import com.country.details.democountry.modal.Country;
import com.country.details.democountry.repository.CountryRepository;
import com.country.details.democountry.util.Client;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    Client client;

    public List<Country> getCountry(){
        return countryRepository.findAll();
    }

    @CircuitBreaker(name = "country", fallbackMethod = "getCountryInfoByNamefallback")
    public String getCountryInfoByName(String countryName){
        return client.webClient().get().uri("/country/into/{name}",countryName).retrieve().bodyToMono(String.class).block();
    }

    public String getCountryInfoByNamefallback(Exception exception){
        return new String();
    }
}
