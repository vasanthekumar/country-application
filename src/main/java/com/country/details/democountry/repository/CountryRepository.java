package com.country.details.democountry.repository;

import com.country.details.democountry.modal.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,Long> {
}
