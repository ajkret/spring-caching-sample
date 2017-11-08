package com.dersommer.sample.caching.config.properties;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;

public interface HazelcastConfigStep {

    void apply(Config joinConfig);
}
