package com.country.details.democountry.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * *
 */
@Entity
@Data
@Table(name="country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @JsonIgnore
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="country_code")
    private String countryCode;
}
