package com.dersommer.sample.caching.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Envelope {

    @JsonProperty("RestResponse")
    private CountriesResponse restResponse;

    public CountriesResponse getRestResponse() {
        return restResponse;
    }

    public void setRestResponse(CountriesResponse restResponse) {
        this.restResponse = restResponse;
    }
}
