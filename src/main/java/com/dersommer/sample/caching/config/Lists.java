package com.dersommer.sample.caching.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix = "lists")
@Component
public class Lists {
    private List<String> aList = new ArrayList<>();

    public List<String> getAList() {
        return aList;
    }

}
