package com.dersommer.sample.caching.service;

import com.dersommer.sample.caching.exception.ApplicationException;
import com.dersommer.sample.caching.model.CountriesResponse;
import com.dersommer.sample.caching.model.Country;
import com.dersommer.sample.caching.repository.CountryRepository;
import com.hazelcast.core.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CountriesService {
    private static Logger LOGGER = LoggerFactory.getLogger(CountriesService.class);

    @Autowired
    private CountryRepository repo;

    @Autowired
    @Qualifier("countriesCacheMap")
    private IMap<String, CountriesResponse> memory;

    public List<Country> getCountry(String filter) {

        LOGGER.info("Actual size of the cache {}", memory.size());
        if(memory.size()>0) {
            memory.entrySet().stream().forEach(entry -> LOGGER.info("key {} value size {}", entry.getKey(), entry.getValue().getCountries().size()));
        }

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
