package com.country.details.democountry.service;

import com.country.details.democountry.dto.CountryInfoDTO;
import com.country.details.democountry.modal.Country;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * Service interface for define service method.
 *
 * @author kvasanthakumar
 * @version 0.0.1
 * Date: April 4,2024
 */
public interface CountryService {
    List<Country> getCountries();
    CountryInfoDTO getCountryInfoByName(String countryName) throws JsonProcessingException;
}
