package com.dersommer.sample.caching.config;

import com.dersommer.sample.caching.config.properties.HazelcastGroup;
import com.dersommer.sample.caching.config.properties.HazelcastNetwork;
import com.dersommer.sample.caching.config.properties.HazelcastNetworkConfiguration;
import com.dersommer.sample.caching.config.properties.HazelcastProperties;
import com.hazelcast.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
/**
 * Hazelcast configuration, including AWS. TCP Ip enabled by default.
 */
public class HazelcastConfiguration {
    private static Logger LOGGER = LoggerFactory.getLogger(HazelcastConfiguration.class);

    @Value("${hazelcast.config.properties.logging:slf4j}")
    private String logging;

    @Autowired
    private List<HazelcastNetworkConfiguration> networkConfigurations;

    @Bean
    public Config configureHazelcast(HazelcastGroup group, HazelcastNetwork network, HazelcastProperties properties) {
        Config config = new Config();
        if (!StringUtils.isEmpty(group.getName())) {
            LOGGER.info("Hazelcast: group configuration ENABLED {} {}", group.getName(), group.getPassword());
            GroupConfig groupConfig = new GroupConfig(group.getName());
            groupConfig.setPassword(group.getPassword());
            config.setGroupConfig(groupConfig);
        }

        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setPort(network.getPort());
        networkConfig.setPortAutoIncrement(network.isPortAutoIncrement());
        LOGGER.info("Hazelcast: Port configured {} auto increment {}", network.getPort(), network.isPortAutoIncrement());
        JoinConfig joinConfig = networkConfig.getJoin();

        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(false);
        joinConfig.getAwsConfig().setEnabled(false);

        networkConfigurations.forEach(item -> item.apply(joinConfig));

        config.setNetworkConfig(networkConfig);

        // Support for additional properties
        config.setProperty("hazelcast.logging.type", logging);
        config.setProperty("hazelcast.discovery.enabled", String.valueOf(properties.isDiscoveryEnabled()));
        config.setProperty("hazelcast.shutdownhook.enabled", String.valueOf(properties.isShutdownHook()));
        config.setProperty("hazelcast.socket.bind.any", String.valueOf(properties.isSocketBindAny()));

        return config;
    }

}
