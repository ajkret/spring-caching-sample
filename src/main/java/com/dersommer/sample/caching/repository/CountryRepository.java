package com.dersommer.sample.caching.repository;

import com.dersommer.sample.caching.model.CountriesResponse;

public interface CountryRepository {

    CountriesResponse getCountries();
}
