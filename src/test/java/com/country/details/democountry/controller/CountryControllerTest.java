package com.country.details.democountry.controller;

import com.country.details.democountry.modal.Country;
import com.country.details.democountry.service.CountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CountryControllerTest {

    @InjectMocks
    CountryController countryController;

    @Mock
    CountryService countryService;

    @Test
    public void testFindAllCountries(){
        Country countryOne=new Country(1,"Finland","F1");
        Country countryTwo =new Country(2 ,"Sweden","S1");
        List<Country> country=new ArrayList<>();
        country.add(countryOne);
        country.add(countryTwo);
        when(countryService.getCountry()).thenReturn(country);
        ResponseEntity<Object> result = countryController.country();
        List<Country> countries = (List<Country>) result.getBody();
        assertThat(countries.get(0).getName().equals(country.get(0).getName()));
    }

}
