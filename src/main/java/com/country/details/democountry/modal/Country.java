package com.country.details.democountry.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * A country with id, name and country code
 *
 * This class allows for setting and getting the country name and county code.
 *
 * @author kvasanthakumar
 * @version 0.0.1
 * Date: April 4,2024
 */
@Entity
@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Table(name="country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @JsonIgnore
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="country_code")
    private String countryCode;

    public Country(int id, String name, String countryCode) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Country(){


    }
}
