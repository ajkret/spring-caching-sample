package com.dersommer.sample.caching.config;

import com.hazelcast.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
/**
 * Hazelcast configuration, including AWS. TCP Ip enabled by default.
 */
public class HazelcastConfiguration {
    private static Logger LOGGER = LoggerFactory.getLogger(HazelcastConfiguration.class);

    @Value("${hazelcast.config.group.name:}")
    private String group;
    @Value("${hazelcast.config.group.password:}")
    private String password;
    @Value("${hazelcast.config.network.port:5705}")
    private int port;
    @Value("${hazelcast.config.network.port-auto-increment:true}")
    private boolean portAutoIncrement;
    @Value("${hazelcast.config.network.multicast.enabled:false}")
    private boolean multicastEnabled;
    @Value("${hazelcast.config.network.multicast.interfaces:false}")
    private String[] multicastInterfaces;
    @Value("${hazelcast.config.network.multicast.port:0}")
    private int multicastPort;
    @Value("${hazelcast.config.network.multicast.group:group}")
    private String multicastGroup;
    @Value("${hazelcast.config.network.multicast.loopback-mode:false}")
    private boolean multicastLoopbackMode;
    @Value("${hazelcast.config.network.tcp-ip.enabled:true}")
    private boolean tcpIpEnabled;
    @Value("${hazelcast.config.network.tcp-ip.members:127.0.0.1}")
    private String[] tcpMembers;
    @Value("${hazelcast.config.network.aws.enabled:false}")
    private boolean awsEnabled;
    @Value("${aws.accessKey:}")
    private String awsAccessKey;
    @Value("${aws.secretKey:}")
    private String awsSecretKey;
    @Value("${aws.region:}")
    private String awsRegion;
    @Value("${hazelcast.config.network.aws.host-header:}")
    private String awsHostHeader;
    @Value("${hazelcast.config.network.aws.security-group-name:}")
    private String awsSecurityGroupName;
    @Value("${hazelcast.config.network.aws.tag-key:}")
    private String awsTagKey;
    @Value("${hazelcast.config.network.aws.tag-value:}")
    private String awsTagValue;

    @Bean
    public Config configureHazelcast() {
        Config config = new Config();
        if (!StringUtils.isEmpty(this.group)) {
            LOGGER.info("Hazelcast: group configuration ENABLED {} {}", this.group, this.password);
            GroupConfig groupConfig = new GroupConfig(this.group);
            groupConfig.setPassword(this.password);
            config.setGroupConfig(groupConfig);
        }

        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setPort(this.port);
        networkConfig.setPortAutoIncrement(this.portAutoIncrement);
        LOGGER.info("Hazelcast: Port configured {} auto increment {}", this.port, this.portAutoIncrement);
        JoinConfig joinConfig = networkConfig.getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(false);
        joinConfig.getAwsConfig().setEnabled(false);
        if (this.tcpIpEnabled) {
            joinConfig.getTcpIpConfig().setEnabled(true);
            LOGGER.info("Hazelcast: TCP IP joiner {}", Arrays.asList(this.tcpMembers)
                                                             .stream()
                                                             .collect(Collectors.joining(",")));
            TcpIpConfig tcpIpConfig = joinConfig.getTcpIpConfig();
            if (this.tcpMembers != null) {
                Stream.of(this.tcpMembers).forEach((member) -> {
                    tcpIpConfig.addMember(member);
                });
            }
        } else if (this.multicastEnabled) {
            joinConfig.getMulticastConfig().setEnabled(true);
            LOGGER.info("Hazelcast: Multicast joiner {}:{} loopback mode={} interfaces {} ", new Object[]{this.multicastGroup, this.multicastPort, this.multicastLoopbackMode, this.multicastInterfaces});
            MulticastConfig multicastConfig = joinConfig.getMulticastConfig();
            multicastConfig.setLoopbackModeEnabled(this.multicastLoopbackMode);
            if (this.multicastGroup != null) {
                multicastConfig.setMulticastGroup(this.multicastGroup);
            }

            if (this.multicastPort > 0) {
                multicastConfig.setMulticastPort(this.multicastPort);
            }

            if (this.multicastInterfaces != null) {
                Stream.of(this.multicastInterfaces).forEach((ip) -> {
                    multicastConfig.addTrustedInterface(ip);
                });
            }
        } else if (this.awsEnabled) {
            joinConfig.getAwsConfig().setEnabled(true);
            LOGGER.info("Hazelcast: AWS discovery enabled AWS Key {} region {}", this.awsAccessKey, this.awsRegion);
            AwsConfig awsConfig = joinConfig.getAwsConfig();
            awsConfig.setAccessKey(this.awsAccessKey);
            awsConfig.setSecretKey(this.awsSecretKey);
            awsConfig.setRegion(this.awsRegion);
            if (!StringUtils.isEmpty(this.awsHostHeader)) {
                LOGGER.info("Hazelcast: AWS Host Header {}", this.awsHostHeader);
                awsConfig.setHostHeader(this.awsHostHeader);
            }

            if (!StringUtils.isEmpty(this.awsSecurityGroupName)) {
                LOGGER.info("Hazelcast: AWS Securiy Group {}", this.awsSecurityGroupName);
                awsConfig.setSecurityGroupName(this.awsSecurityGroupName);
            }

            if (!StringUtils.isEmpty(this.awsTagKey)) {
                LOGGER.info("Hazelcast: AWS Tag key / value {}", this.awsTagKey, this.awsTagValue);
                awsConfig.setSecurityGroupName(this.awsTagKey);
                awsConfig.setSecurityGroupName(this.awsTagValue);
            }
        }

        config.setNetworkConfig(networkConfig);

        // Logging
        config.setProperty("hazelcast.logging.type", "slf4j");
        return config;
    }
}
