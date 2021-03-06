package com.dersommer.sample.caching.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Maps the entire response from http://services.groupkt.com/country/get/all
 */
public class CountriesResponse implements Serializable {
    private static final long serialVersionUID = 1;

    @JsonProperty("messages")
    private String[] messages;

    @JsonProperty("result")
    private List<Country> countries;

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public String[] getMessages() {
        return messages;
    }

    public void setMessages(String[] messages) {
        this.messages = messages;
    }

}
