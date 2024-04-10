package com.country.details.democountry.controller;

import com.country.details.democountry.modal.Country;
import com.country.details.democountry.service.CountryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
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
    private CountryServiceImpl countryService;

    @InjectMocks
    private CountryController countryController;

    @Test
    public void testFindAllCountries() throws Exception {
        //Mock data
        Country countryOne = getCountry(1, "Finland", "F1");
        Country countryTwo = getCountry(2, "Sweden", "S1");
        List<Country> country = new ArrayList<>();
        country.add(countryOne);
        country.add(countryTwo);

        //Mock the service method to return the list of countries.
        when(countryService.getCountry()).thenReturn(country);


        //perform GET request to "/countries" endpoint.
        mockMvc.perform(get("/country/countries")
                .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists()) //Assert that "data" field exists
                .andExpect(jsonPath("$.data").isArray()); //Assert that "data" fields is array
    }

    @Test
    public void testCountryByName() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/country/countries/Finland")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
    }


    private Country getCountry(int id,String name,String countryCode){
        Country country = new Country();
        country.setId(id);
        country.setName(name);
        country.setCountryCode(countryCode);
        return country;
    }

}
