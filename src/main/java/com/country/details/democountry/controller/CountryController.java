package com.country.details.democountry.controller;

import com.country.details.democountry.dto.CountryInfoDTO;
import com.country.details.democountry.modal.Country;
import com.country.details.democountry.service.CountryServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Contains country information related Api calls .
 * @author kvasanthakumar
 * @version 0.0.1
 * Date: April 4,2024
 */
@RestController
@RequestMapping(value = "/country")
public class CountryController {

    /**
     * Properties injection to autowire CountryService dependency.
     */
    @Autowired
    CountryServiceImpl countryService;

    /**
     * Description : Get all countries.
     * Endpoint: GET /country/countries
     * @return Response Entity
     */
    @GetMapping(value = "/countries")
    public ResponseEntity<List<Country>> country() {
        return ResponseEntity.ok(countryService.getCountries());
    }


    /**
     * Description : Get a country fields by Name.
     * Endpoint: GET /country/countries/{name}
     * @param name The name of the country
     * @return The Response entity with specified Name,Population etc
     */
    @GetMapping(value = "/countries/{name}")
    public ResponseEntity<CountryInfoDTO> country(@PathVariable @NotNull String name) {
        CountryInfoDTO countryInfoDTO = null;
        try {
            countryInfoDTO = countryService.getCountryInfoByName(name);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(countryInfoDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> errorEndpoint(RuntimeException ex){
        String errorMessage = "Internal Server Error";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @GetMapping("/evict")
    public ResponseEntity<Object> evictCache(){
        countryService.evictCache();
        return ResponseEntity.ok("Cache eviction triggered successfully");
    }

}
