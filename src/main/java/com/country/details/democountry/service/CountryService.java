package com.country.details.democountry.service;

import com.country.details.democountry.modal.Country;
import com.country.details.democountry.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public List<Country> getCountry(){
        return countryRepository.findAll();
    }
}
