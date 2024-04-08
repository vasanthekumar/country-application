package com.country.details.democountry.controller;

import com.country.details.democountry.service.CountryService;
import com.country.details.democountry.util.ResponseHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Contains country infomation related Api calls .
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
    CountryService countryService;

    /**
     * Description : Get all countries.
     * Endpoint: GET /api/countries
     * @return Response Entity
     */
    @GetMapping(value = "/countries")
    public ResponseEntity<Object> country() {
       return ResponseHandler.responseBuilder("Countries are given here", HttpStatus.OK,countryService.getCountry());
    }


    /**
     * Description : Get a country fields by Name.
     * Endpoint: GET /api/countries/{name}
     * @param name The name of the country
     * @param fields The required fields of the country
     * @return The Response entity with specified Name,Population
     */
    @GetMapping(value = "/countries/{name}")
    public ResponseEntity<Object> country(@PathVariable String name) {
        ResponseEntity<Object> response = null;
        try {
           response = ResponseHandler.responseBuilder("Requested country details are given here",HttpStatus.OK,countryService.getCountryInfoByName(name));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return response;
    }

}
