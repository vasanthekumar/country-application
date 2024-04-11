package com.country.details.democountry.controller;

import com.country.details.democountry.dto.CountryInfoDTO;
import com.country.details.democountry.modal.Country;
import com.country.details.democountry.service.CountryServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Contains country related Api calls .
 * @author kvasanthakumar
 * @version 0.0.1
 * Date: April 4,2024
 */
@RestController
@Slf4j
@RequestMapping(value = "/country")
@CrossOrigin
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
        log.info("Calling country method with Get endpoint...");
        return ResponseEntity.ok(countryService.getCountries());
    }


    /**
     * Description : Get a country details by Name.
     * Endpoint: GET /country/countries/{name}
     * @param name The name of the country
     * @return The Response entity with specified Name,Population etc
     */
    @GetMapping(value = "/countries/{name}")
    public ResponseEntity<CountryInfoDTO> country(@PathVariable @NotNull String name) {
        log.info("Calling country method with the county name : {}", name);
        CountryInfoDTO countryInfoDTO = null;
        try {
            countryInfoDTO = countryService.getCountryInfoByName(name);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(countryInfoDTO);
    }

    /**
     * Description : Exception handler.
     * @param ex Runtime exception
     * @return The Response entity with error message.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> errorEndpoint(RuntimeException ex){
        String errorMessage = "Internal Server Error";
        log.info("Exception occurred {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    /**
     * Description : Cache evict method to evict the cache data.
     * @return The Response entity with success message.
     */
    @GetMapping("/evict")
    public ResponseEntity<Object> evictCache(){
        countryService.evictCache();
        return ResponseEntity.ok("Cache eviction triggered successfully");
    }

}
