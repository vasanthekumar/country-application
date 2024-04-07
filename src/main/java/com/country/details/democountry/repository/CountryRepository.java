package com.country.details.democountry.repository;

import com.country.details.democountry.modal.Country;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing entities of type Country.
 *
 * This repository provides CRUD (Create,Read,Update,Delete) operations for entities of type Country.
 *
 * @author kvasanthakumar
 * @version 0.0.1
 * Date: April 4,2024
 */
public interface CountryRepository extends JpaRepository<Country,Integer> {
    //TODO
}
