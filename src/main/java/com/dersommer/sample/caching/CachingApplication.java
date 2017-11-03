package com.dersommer.sample.caching;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableAutoConfiguration
@EnableScheduling
/**
 * Spring bootstrap class
 */
public class CachingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CachingApplication.class, args);
    }
}
