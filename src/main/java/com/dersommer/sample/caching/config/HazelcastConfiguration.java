package com.dersommer.sample.caching.config;

import com.dersommer.sample.caching.config.properties.HazelcastGroup;
import com.dersommer.sample.caching.config.properties.HazelcastNetwork;
import com.dersommer.sample.caching.config.properties.HazelcastConfigurationStep;
import com.dersommer.sample.caching.config.properties.HazelcastProperties;
import com.hazelcast.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.List;

@Configuration
/**
 * Hazelcast configuration, including AWS. TCP Ip enabled by default.
 */
public class HazelcastConfiguration {
    private static Logger LOGGER = LoggerFactory.getLogger(HazelcastConfiguration.class);

    @Value("${hazelcast.config.properties.logging:slf4j}")
    private String logging;

    @Autowired
    private List<HazelcastConfigurationStep> configurations;

    @Bean
    public Config configureHazelcast(HazelcastGroup group, HazelcastNetwork network, HazelcastProperties properties) {
        Config config = new Config();
        NetworkConfig networkConfig = new NetworkConfig();
        config.setNetworkConfig(networkConfig);
        networkConfig.getJoin().getMulticastConfig().setEnabled(false);
        networkConfig.getJoin().getTcpIpConfig().setEnabled(false);
        networkConfig.getJoin().getAwsConfig().setEnabled(false);

        configurations.forEach(item -> item.apply(config));

        return config;
    }

}
