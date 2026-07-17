package com.cognizant.springlearn.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cognizant.springlearn.Country;
import com.cognizant.springlearn.CountryNotFoundException;
import com.cognizant.springlearn.repository.CountryRepository;

@RestController
public class HelloController {

    @Autowired
    private CountryRepository countryRepository;

    // Hands-on 1: Greeting
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World!!";
    }

    // Hands-on 2: Get all countries
    @GetMapping("/countries")
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    // Hands-on 2 (Extended): Add/Store a country
    @PostMapping("/countries")
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        Country savedCountry = countryRepository.save(country);
        return ResponseEntity.ok(savedCountry);
    }

    // Hands-on 3: Get country based on country code path variable
    @GetMapping("/countries/{code}")
    public Country getCountryByCode(@PathVariable String code) throws CountryNotFoundException {
        return countryRepository.findById(code.toUpperCase())
                .orElseThrow(() -> new CountryNotFoundException("Country with code " + code + " not found."));
    }
}