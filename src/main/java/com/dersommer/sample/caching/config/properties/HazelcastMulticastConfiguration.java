package com.dersommer.sample.caching.config.properties;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MulticastConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
@ConfigurationProperties(prefix = "hazelcast.config.network.multicast")
public class HazelcastMulticastConfiguration implements HazelcastConfigurationStep {
    private static Logger LOGGER = LoggerFactory.getLogger(HazelcastMulticastConfiguration.class);

    private boolean enabled;
    private String[] interfaces;

    private int port;

    private String group;

    private boolean loopbackMode;

    @Override
    public void apply(Config config) {
        if(enabled) {
            JoinConfig joinConfig = config.getNetworkConfig().getJoin();

            joinConfig.getMulticastConfig().setEnabled(true);
            LOGGER.info("Hazelcast: Multicast joiner {}:{} loopback mode={} interfaces {} ", new Object[]{this.group, this.port, this.loopbackMode, this.interfaces});

            MulticastConfig multicastConfig = joinConfig.getMulticastConfig();

            multicastConfig.setLoopbackModeEnabled(this.loopbackMode);
            if (this.group != null) {
                multicastConfig.setMulticastGroup(this.group);
            }

            if (this.port > 0) {
                multicastConfig.setMulticastPort(this.port);
            }

            if (this.interfaces != null) {
                Stream.of(this.interfaces).forEach((ip) -> {
                    multicastConfig.addTrustedInterface(ip);
                });
            }
        }
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getInterfaces() {
        return interfaces;
    }

    public void setInterfaces(String[] interfaces) {
        this.interfaces = interfaces;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isLoopbackMode() {
        return loopbackMode;
    }

    public void setLoopbackMode(boolean loopbackMode) {
        this.loopbackMode = loopbackMode;
    }
}
