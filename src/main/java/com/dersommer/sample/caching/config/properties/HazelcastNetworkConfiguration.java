package com.dersommer.sample.caching.config.properties;

import com.hazelcast.config.JoinConfig;

public interface HazelcastNetworkConfiguration {

    void apply(JoinConfig joinConfig);
}
