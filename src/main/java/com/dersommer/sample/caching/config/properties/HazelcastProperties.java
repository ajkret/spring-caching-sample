package com.dersommer.sample.caching.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix="hazelcast.config.properties")
public class HazelcastProperties {

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


    //    @Value("${hazelcast.config.network.port:5705}")
//    private int port;
//    @Value("${hazelcast.config.network.port-auto-increment:true}")
//    private boolean portAutoIncrement;
//    @Value("${hazelcast.config.network.multicast.enabled:false}")
//    private boolean multicastEnabled;
//    @Value("${hazelcast.config.network.multicast.interfaces:false}")
//    private String[] multicastInterfaces;
//    @Value("${hazelcast.config.network.multicast.port:0}")
//    private int multicastPort;
//    @Value("${hazelcast.config.network.multicast.group:group}")
//    private String multicastGroup;
//    @Value("${hazelcast.config.network.multicast.loopback-mode:false}")
//    private boolean multicastLoopbackMode;
//    @Value("${hazelcast.config.network.tcp-ip.enabled:true}")
//    private boolean tcpIpEnabled;
//    @Value("${hazelcast.config.network.tcp-ip.members:127.0.0.1}")
//    private String[] tcpMembers;
//    @Value("${hazelcast.config.network.aws.enabled:false}")
//    private boolean awsEnabled;
//    @Value("${aws.accessKey:}")
//    private String awsAccessKey;
//    @Value("${aws.secretKey:}")
//    private String awsSecretKey;
//    @Value("${aws.region:}")
//    private String awsRegion;
//    @Value("${hazelcast.config.network.aws.host-header:}")
//    private String awsHostHeader;
//    @Value("${hazelcast.config.network.aws.security-group-name:}")
//    private String awsSecurityGroupName;
//    @Value("${hazelcast.config.network.aws.tag-key:}")
//    private String awsTagKey;
//    @Value("${hazelcast.config.network.aws.tag-value:}")
//    private String awsTagValue;

}
