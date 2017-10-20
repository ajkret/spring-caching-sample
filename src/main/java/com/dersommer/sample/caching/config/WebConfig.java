package com.dersommer.sample.caching.config;

import com.dersommer.sample.caching.resource.QueryCountry;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig extends ResourceConfig {

    public WebConfig() {
        register(QueryCountry.class);

    }
}
