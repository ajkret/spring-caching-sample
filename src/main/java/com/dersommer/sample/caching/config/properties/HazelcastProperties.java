package com.dersommer.sample.caching.config.properties;

import com.hazelcast.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix="hazelcast.config.properties")
public class HazelcastProperties implements HazelcastConfigurationStep {

    private boolean discoveryEnabled = true;

    private boolean shutdownHook = true;

    private boolean socketBindAny = false;

    private String logging = "slf4j";

    public boolean isDiscoveryEnabled() {
        return discoveryEnabled;
    }

    public void setDiscoveryEnabled(boolean discoveryEnabled) {
        this.discoveryEnabled = discoveryEnabled;
    }

    public boolean isShutdownHook() {
        return shutdownHook;
    }

    public void setShutdownHook(boolean shutdownHook) {
        this.shutdownHook = shutdownHook;
    }

    public boolean isSocketBindAny() {
        return socketBindAny;
    }

    public void setSocketBindAny(boolean socketBindAny) {
        this.socketBindAny = socketBindAny;
    }

    @Override
    public void apply(Config config) {
        // Support for additional properties
        config.setProperty("hazelcast.logging.type", logging);
        config.setProperty("hazelcast.discovery.enabled", String.valueOf(isDiscoveryEnabled()));
        config.setProperty("hazelcast.shutdownhook.enabled", String.valueOf(isShutdownHook()));
        config.setProperty("hazelcast.socket.bind.any", String.valueOf(isSocketBindAny()));
    }
}
