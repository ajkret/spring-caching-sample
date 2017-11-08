package com.dersommer.sample.caching.config.properties;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ConfigurationProperties(prefix = "hazelcast.config.group")
public class HazelcastGroup implements HazelcastConfigurationStep {
    private static Logger LOGGER = LoggerFactory.getLogger(HazelcastGroup.class);

    private String name;

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void apply(Config config) {
        if (!StringUtils.isEmpty(name)) {
            LOGGER.info("Hazelcast: group configuration ENABLED {} {}", name, password);
            GroupConfig groupConfig = new GroupConfig(name);
            groupConfig.setPassword(password);
            config.setGroupConfig(groupConfig);
        }
    }
}
