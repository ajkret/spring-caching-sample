package com.dersommer.sample.caching.config.properties;

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
@ConfigurationProperties(prefix="hazelcast.config.network.tcp-ip")
public class HazelcastNetworkTcpIp implements HazelcastNetworkConfiguration {
    public static Logger LOGGER = LoggerFactory.getLogger(HazelcastNetworkTcpIp.class);

    private boolean enabled;

    private String[] members;

    @Override
    public void apply(JoinConfig joinConfig) {
        if(enabled) {
            joinConfig.getTcpIpConfig().setEnabled(true);
            LOGGER.info("Hazelcast: TCP IP joiner {}", Arrays.asList(this.members)
                                                             .stream()
                                                             .collect(Collectors.joining(",")));
            TcpIpConfig tcpIpConfig = joinConfig.getTcpIpConfig();
            if (this.members != null) {
                Stream.of(this.members).forEach((member) -> {
                    tcpIpConfig.addMember(member);
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
