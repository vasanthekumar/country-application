package com.country.details.democountry.service;

import com.country.details.democountry.repository.CountryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

@ExtendWith(MockitoExtension.class)
public class CountryServiceTest {

    @InjectMocks
    CountryRepository countryRepository;

    @InjectMocks
    LoadBalancerClient loadBalancerClient;

    @Test
    public void testGetCountries() {


    }
}
