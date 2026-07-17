package com.cognizant.ormlearn.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    @Transactional(readOnly = true)
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    // Hands-on 1 (Tasks 1 & 2): Search by partial string match ordered alphabetically
    @Transactional(readOnly = true)
    public List<Country> searchCountriesByNamePart(String part) {
        return countryRepository.findByNameContainingOrderByNameAsc(part);
    }

    // Hands-on 1 (Task 3): Filter by starting alphabet letter
    @Transactional(readOnly = true)
    public List<Country> findCountriesStartingWith(String letter) {
        return countryRepository.findByNameStartingWithOrderByNameAsc(letter);
    }

	public Country findCountryByCode(String target) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addCountry(Country c) {
		// TODO Auto-generated method stub
		
	}

	public void updateCountry(String upperCase, String trim) {
		// TODO Auto-generated method stub
		
	}

	public void deleteCountry(String upperCase) {
		// TODO Auto-generated method stub
		
	}
}