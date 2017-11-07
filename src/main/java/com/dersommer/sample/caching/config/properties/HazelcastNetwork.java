package com.dersommer.sample.caching.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hazelcast.config.network")
public class HazelcastNetwork {

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
}
