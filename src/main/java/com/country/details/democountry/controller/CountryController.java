package com.country.details.democountry.controller;

import com.country.details.democountry.modal.Country;
import com.country.details.democountry.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Country> country() {
        return countryService.getCountry();
    }


    /**
     * *
     * @param name
     * @return
     */
    @GetMapping(value = "/countries/{name}")
    public String country(@PathVariable String name) {
        return countryService.getCountryInfoByName(name);
    }

}
