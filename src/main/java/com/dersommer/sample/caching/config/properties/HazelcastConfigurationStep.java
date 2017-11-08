package com.dersommer.sample.caching.config.properties;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;

public interface HazelcastConfigurationStep {

    void apply(Config config);
}
