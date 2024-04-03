package com.country.details.democountry.controller;

import com.country.details.democountry.modal.Country;
import com.country.details.democountry.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * *
 */
@RestController
public class CountryController {

    @Autowired
    CountryService countryService;
    /**
     * *
     * @return
     */
    @RequestMapping(value = "/countries",method = RequestMethod.GET)
    public List<Country> country(){
        return countryService.getCountry();
    }
}
