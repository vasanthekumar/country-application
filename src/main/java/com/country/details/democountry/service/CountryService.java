package com.country.details.democountry.service;

import com.country.details.democountry.modal.Country;
import com.country.details.democountry.repository.CountryRepository;
import com.country.details.democountry.util.Client;
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

    public String getCountryInfoByName(String countryName){
        return client.webClient().get().uri("/into/{name}",countryName).retrieve().bodyToMono(String.class).block();
    }
}
