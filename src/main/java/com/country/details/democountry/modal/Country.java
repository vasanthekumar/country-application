package com.country.details.democountry.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

/**
 * *
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
