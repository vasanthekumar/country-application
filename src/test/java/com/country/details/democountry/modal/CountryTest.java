package com.country.details.democountry.modal;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CountryTest {

    @Mock
    private Country country;

    @Test
    public void setCountryName(){
        country = new Country();
        country.setName("France");
        assertEquals("France",country.getName());
    }
}
