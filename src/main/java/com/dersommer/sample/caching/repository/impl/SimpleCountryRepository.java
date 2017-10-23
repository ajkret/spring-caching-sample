package com.dersommer.sample.caching.repository.impl;

import com.dersommer.sample.caching.exception.ApplicationException;
import com.dersommer.sample.caching.model.CountriesResponse;
import com.dersommer.sample.caching.model.Envelope;
import com.dersommer.sample.caching.repository.CountryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

@Component
public class SimpleCountryRepository implements CountryRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(SimpleCountryRepository.class);

    @Value("${contries.baseUrl:http://services.groupkt.com/country/get}")
    private String baseUrl;

    private static ThreadLocal<RestTemplate> restTemplate = new ThreadLocal<>();

    private static ThreadLocal<ObjectMapper> jsonParser = new ThreadLocal<>();

    @Override
    @Cacheable("countriesCacheMap")
    public CountriesResponse getCountries() {
        LOGGER.info("Requesting new Data...");
        return retrieveFlightInfo();
    }

    /**
     * Call the services using Spring's RequestEntity / RestTemplate
     * @return Response from the service
     */
    private CountriesResponse retrieveFlightInfo() {
        String filter = "all";


        // Configure
        RequestEntity<Void> request = configureRequest(filter);

        // Call
        ResponseEntity<String> entity = getRestTemplate().exchange(request, String.class);

        // Validate
        validateResponse(entity);

        // Transform
        return transformResponse(entity);
    }

    private RequestEntity<Void> configureRequest(String filter) {
        return RequestEntity.get(
                URI.create(String.format("%s/%s", baseUrl, "all")))
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private void validateResponse(ResponseEntity<String> entity) {
        // Validate
        if (entity == null || !HttpStatus.OK.equals(entity.getStatusCode())) {
            LOGGER.error("Failed to retrieve data, code = {}", entity != null ? entity.getStatusCode() : -1);
            throw new ApplicationException("Failed to retrieve data");
        }
        if (entity.getBody() == null) {
            LOGGER.error("Call to Retrieve data failed, code = {}", entity.getStatusCode());
            throw new ApplicationException("Failed to retrieve data");
        }
    }

    private CountriesResponse transformResponse(ResponseEntity<String> entity) {
        try {
            Envelope envelope = getJsonParser().readValue(entity.getBody(), Envelope.class);
            return envelope.getRestResponse();
        } catch (IOException e) {
            LOGGER.error("Could not parse response {} ", entity.getBody());
            throw new ApplicationException("Failed to retrieve data");
        }
    }

    private static RestTemplate getRestTemplate() {
        if (restTemplate.get() == null) {
            restTemplate.set(new RestTemplate());
        }
        return restTemplate.get();
    }

    private static ObjectMapper getJsonParser() {
        if (jsonParser.get() == null) {
            jsonParser.set(new ObjectMapper());
        }
        return jsonParser.get();
    }

    @CacheEvict(cacheNames="countriesCacheMap", allEntries = true)
    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    public void clearCache() {
        LOGGER.info("Clearing Cache");
    }

}
