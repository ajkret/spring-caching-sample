package com.dersommer.sample.caching.config.properties;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.TcpIpConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@ConfigurationProperties(prefix = "hazelcast.config.network.tcp-ip")
public class HazelcastTcpIp implements HazelcastConfigurationStep {
    public static Logger LOGGER = LoggerFactory.getLogger(HazelcastTcpIp.class);

    private boolean enabled;

    private String[] members;

    @Override
    public void apply(Config config) {
        if (enabled) {
            JoinConfig joinConfig = config.getNetworkConfig().getJoin();
            joinConfig.getTcpIpConfig().setEnabled(true);

            if (this.members != null) {
                LOGGER.info("Hazelcast: TCP IP joiner {}", Arrays.asList(this.members)
                                                                 .stream()
                                                                 .collect(Collectors.joining(",")));
                Stream.of(this.members).forEach((member) -> {
                    joinConfig.getTcpIpConfig().addMember(member);
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

    public String[] getMembers() {
        return members;
    }

    public void setMembers(String[] members) {
        this.members = members;
    }
}
