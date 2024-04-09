package com.country.details.democountry.service;

import com.country.details.democountry.dto.CountryInfoDTO;
import com.country.details.democountry.repository.CountryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CountryServiceTest {
    @Autowired
    CountryService countryService;

    static WireMockServer wireMockServer;

    @BeforeAll
    public static void setUp(){

        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", 8080);

        wireMockServer.stubFor(get(urlEqualTo("/country/info/Finland"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody("[{\"name\":{\"common\":\"Finland\"},\"flags\":{\"svg\":\"https://flagcdn.com/fi.svg\"},\"capital\":[\"Helsinki\"],\"cca2\":\"FI\",\"population\":5530719}]"))
        );

    }
    @Test
    public void testGetCountryInfoByName() throws JsonProcessingException {
        CountryInfoDTO countryInfoDTO = countryService.getCountryInfoByName("Finland");
        // Verify that the expected data is extracted from the response
        assertEquals("Finland", countryInfoDTO.name());
        assertEquals("https://flagcdn.com/fi.svg", countryInfoDTO.flagFileUrl());
        assertEquals("Helsinki", countryInfoDTO.capital());
        assertEquals("FI", countryInfoDTO.countryCode());
        assertEquals(5530719, countryInfoDTO.population());
    }
}
