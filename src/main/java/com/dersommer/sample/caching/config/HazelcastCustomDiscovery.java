package com.dersommer.sample.caching.config;

import com.dersommer.sample.caching.config.properties.HazelcastConfigurationStep;
import com.hazelcast.config.Config;
import com.hazelcast.config.DiscoveryStrategyConfig;
import com.hazelcast.config.JoinConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Profile("docker")
@Component
@ConfigurationProperties(prefix="hazelcast.config.network.docker")
public class HazelcastCustomDiscovery implements HazelcastConfigurationStep {

    private boolean enabled;

    private String[] networkNames;

    private String[] serviceNames;

    private String[] serviceLabels;

    private String appName;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String[] getNetworkNames() {
        return networkNames;
    }

    public void setNetworkNames(String[] networkNames) {
        this.networkNames = networkNames;
    }

    public String[] getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(String[] serviceNames) {
        this.serviceNames = serviceNames;
    }

    public String[] getServiceLabels() {
        return serviceLabels;
    }

    public void setServiceLabels(String[] serviceLabels) {
        this.serviceLabels = serviceLabels;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getPeerPort() {
        return peerPort;
    }

    public void setPeerPort(int peerPort) {
        this.peerPort = peerPort;
    }

    @Value("${hazelcast.config.network.port:5701}")
    private int peerPort;

//       <discovery-strategy enabled="true" class="org.bitsofinfo.hazelcast.discovery.docker.swarm.DockerSwarmDiscoveryStrategy">
//          <properties>
//            <!-- Comma delimited list of Docker network names to discover matching services on -->
//            <property name="docker-network-names">${dockerNetworkNames}</property>
//            <!-- Comma delimited list of relevant Docker service names to find tasks/containers on the above networks -->
//            <property name="docker-service-names">${dockerServiceNames}</property>
//            <!-- Comma delimited list of relevant Docker service label=values to find tasks/containers on the above networks -->
//            <property name="docker-service-labels">${dockerServiceLabels}</property>
//            <!-- The raw port that hazelcast is listening on
//
//    IMPORTANT: this is NOT a docker "published" port, nor is it necessarily
//    a EXPOSEd port... it is simply the hazelcast port that the service
//    is configured with, this must be the same for all matched containers
//    in order to work, and just using the default of 5701 is the simplist
//    way to go.
//            -->
//            <property name="hazelcast-peer-port">${hazelcastPeerPort}</property>
//          </properties>
//        </discovery-strategy>

    @Override
    public void apply(Config config) {
        if (enabled) {
            JoinConfig joinConfig = config.getNetworkConfig().getJoin();

            DiscoveryStrategyConfig strategyConfig = new DiscoveryStrategyConfig("org.bitsofinfo.hazelcast.discovery.docker.swarm.DockerSwarmDiscoveryStrategy");
            strategyConfig.addProperty("docker-network-names", Arrays.asList(networkNames)
                                                                     .stream()
                                                                     .collect(Collectors.joining(",")));
            strategyConfig.addProperty("docker-service-names", Arrays.asList(serviceNames)
                                                                     .stream()
                                                                     .collect(Collectors.joining(",")));
            strategyConfig.addProperty("docker-service-labels", Arrays.asList(serviceLabels)
                                                                      .stream()
                                                                      .collect(Collectors.joining(",")));
            strategyConfig.addProperty("hazelcast-peer-port", String.valueOf(peerPort));
            joinConfig.getDiscoveryConfig().addDiscoveryStrategyConfig(strategyConfig);
        }
    }
}
