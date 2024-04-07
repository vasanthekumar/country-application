package com.country.details.democountry.controller;

import com.country.details.democountry.service.CountryService;
import com.country.details.democountry.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * *
 */
@RestController
@RefreshScope
@RequestMapping(value = "/country")
public class CountryController {

    @Autowired
    CountryService countryService;

    /**
     * *
     *
     * @return
     */
    @GetMapping(value = "/countries")
    public ResponseEntity<Object> country() {
       return ResponseHandler.responseBuilder("Countries are given here", HttpStatus.OK,countryService.getCountry());
    }


    /**
     * *
     * @param name
     * @return
     */
    @GetMapping(value = "/countries/{name}")
    public ResponseEntity<Object> country(@PathVariable String name,@RequestParam String fields) {
        System.out.println("Fields :"+fields);
        return ResponseHandler.responseBuilder("Requested country details are given here",HttpStatus.OK,countryService.getCountryInfoByName(name,fields));
    }

}
