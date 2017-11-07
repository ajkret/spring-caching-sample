package com.dersommer.sample.caching.config;

import com.dersommer.sample.caching.model.CountriesResponse;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class HazelcastCountriesMapConfiguration {
    private static Logger LOGGER = LoggerFactory.getLogger(HazelcastCountriesMapConfiguration.class);

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Autowired
    private Config config;

    @Bean("countriesCacheMap")
    public IMap<String, CountriesResponse> createCountriesCache() {
        return hazelcastInstance.getMap("countriesCacheMap");
    }

    @PostConstruct
    public void init() {
        config.addMapConfig(new MapConfig().setName("countriesCacheMap"));
    }

    /**
     * This is where the real magic integration with Hazelcast happens. Thanks Hazelcast.
     * @return
     */
    @Bean
    public CacheManager cacheManager() {
        LOGGER.info("Using Hazelcast Cache Manager");
        return new HazelcastCacheManager(hazelcastInstance);
    }
}
