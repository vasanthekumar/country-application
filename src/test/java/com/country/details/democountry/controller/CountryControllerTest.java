package com.country.details.democountry.controller;

import com.country.details.democountry.modal.Country;
import com.country.details.democountry.service.CountryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(CountryController.class)
public class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @InjectMocks
    private CountryController countryController;

    @Test
    public void testFindAllCountries() throws Exception {
        //Mock data
        Country countryOne = new Country(1, "Finland", "F1");
        Country countryTwo = new Country(2, "Sweden", "S1");
        List<Country> country = new ArrayList<>();
        country.add(countryOne);
        country.add(countryTwo);

        //Mock the service method to return the list of countries.
        when(countryService.getCountry()).thenReturn(country);

        ResponseEntity<Object> result = countryController.country();
        HashMap<String, Object> response = (HashMap<String, Object>) result.getBody();
        List<Country> countries = (List<Country>) response.get("data");
        assertThat(countries.size() == countries.size());
        assertThat(countries.get(0).getName().equals(country.get(0).getName()));
        assertThat(countries.get(1).getCountryCode().equals(country.get(1).getCountryCode()));

        //perform GET request to "/countries" endpoint.
        mockMvc.perform(get("/api/countries")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists()) //Assert that "data" field exists
                .andExpect(jsonPath("$.data").isArray()); //Assert that "data" fields is array
    }

}
