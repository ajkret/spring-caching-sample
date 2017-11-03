package com.dersommer.sample.caching.config;

import com.dersommer.sample.caching.resource.QueryCountry;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class WebConfig extends ResourceConfig {

    private static Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

    @Autowired
    private Lists lists;

    public WebConfig() {
        register(QueryCountry.class);
    }

    @PostConstruct
    public void init() {
        lists.getAList().forEach(item -> LOGGER.info(item));

    }
}
