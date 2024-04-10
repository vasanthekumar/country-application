package com.country.details.democountry.service;

import com.country.details.democountry.dto.CountryInfoDTO;
import com.country.details.democountry.modal.Country;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface CountryService {
    List<Country> getCountries();
    CountryInfoDTO getCountryInfoByName(String countryName) throws JsonProcessingException;
}
