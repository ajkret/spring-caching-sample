package com.dersommer.sample.caching.config.properties;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hazelcast.config.network")
public class HazelcastNetwork implements HazelcastConfigurationStep {
    private static Logger LOGGER = LoggerFactory.getLogger(HazelcastNetwork.class);

    int port;

    boolean portAutoIncrement;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isPortAutoIncrement() {
        return portAutoIncrement;
    }

    public void setPortAutoIncrement(boolean portAutoIncrement) {
        this.portAutoIncrement = portAutoIncrement;
    }

    @Override
    public void apply(Config config) {
        NetworkConfig networkConfig = config.getNetworkConfig();

        networkConfig.setPort(port);
        networkConfig.setPortAutoIncrement(portAutoIncrement);
        LOGGER.info("Hazelcast: Port configured {} auto increment {}", port, portAutoIncrement);
    }
}
