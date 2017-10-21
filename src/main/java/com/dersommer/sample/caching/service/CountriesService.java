package com.dersommer.sample.caching.service;

import com.dersommer.sample.caching.exception.ApplicationException;
import com.dersommer.sample.caching.model.CountriesResponse;
import com.dersommer.sample.caching.model.Country;
import com.dersommer.sample.caching.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountriesService {

    @Autowired
    private CountryRepository repo;

    public List<Country> getCountry(String filter) {

        CountriesResponse countries = repo.getCountries();

        if (countries != null) {
            return countries.getCountries()
                            .stream()
                            .filter(country -> country.getName().contains(filter))
                            .collect(Collectors.toList());
        }

        throw new ApplicationException("No data found");
    }
}
