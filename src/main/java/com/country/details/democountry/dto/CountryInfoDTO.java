package com.country.details.democountry.dto;

import lombok.Data;

@Data
public class CountryInfoDTO {
    private String name;
    private String countryCode;
    private String capital;
    private double population;
    private String flagFileUrl;
}
