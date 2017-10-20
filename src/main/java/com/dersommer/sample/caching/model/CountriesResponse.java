package com.dersommer.sample.caching.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Maps the entire response from http://services.groupkt.com/country/get/all
 */
public class CountriesResponse {

    @JsonProperty("messages")
    private String[] messages;

    @JsonProperty("result")
    private Country[] countries;

    public String[] getMessages() {
        return messages;
    }

    public void setMessages(String[] messages) {
        this.messages = messages;
    }

    public Country[] getCountries() {
        return countries;
    }

    public void setCountries(Country[] countries) {
        this.countries = countries;
    }
}
