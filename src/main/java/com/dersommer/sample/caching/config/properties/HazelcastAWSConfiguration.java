package com.dersommer.sample.caching.config.properties;

import com.hazelcast.config.AwsConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ConfigurationProperties(prefix = "hazelcast.config.network.aws")
public class HazelcastAWSConfiguration implements HazelcastConfigurationStep {
    private static Logger LOGGER = LoggerFactory.getLogger(HazelcastAWSConfiguration.class);

    @Value("${aws.accessKey:}")
    private String awsAccessKey;

    @Value("${aws.secretKey:}")
    private String awsSecretKey;

    @Value("${aws.region:}")
    private String awsRegion;

    private boolean enabled;

    private String hostHeader;

    private String securityGroupName;

    private String tagKey;

    private String tagValue;

    @Override
    public void apply(Config config) {
        if (enabled) {
            JoinConfig joinConfig = config.getNetworkConfig().getJoin();

            joinConfig.getAwsConfig().setEnabled(true);
            LOGGER.info("Hazelcast: AWS discovery enabled AWS Key {} region {}", this.awsAccessKey, this.awsRegion);
            AwsConfig awsConfig = joinConfig.getAwsConfig();
            awsConfig.setAccessKey(this.awsAccessKey);
            awsConfig.setSecretKey(this.awsSecretKey);
            awsConfig.setRegion(this.awsRegion);
            if (!StringUtils.isEmpty(this.hostHeader)) {
                LOGGER.info("Hazelcast: AWS Host Header {}", this.hostHeader);
                awsConfig.setHostHeader(this.hostHeader);
            }

            if (!StringUtils.isEmpty(this.securityGroupName)) {
                LOGGER.info("Hazelcast: AWS Securiy Group {}", this.securityGroupName);
                awsConfig.setSecurityGroupName(this.securityGroupName);
            }

            if (!StringUtils.isEmpty(this.tagKey)) {
                LOGGER.info("Hazelcast: AWS Tag key / value {}", this.tagKey, this.tagValue);
                awsConfig.setSecurityGroupName(this.tagKey);
                awsConfig.setSecurityGroupName(this.tagValue);
            }
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getHostHeader() {
        return hostHeader;
    }

    public void setHostHeader(String hostHeader) {
        this.hostHeader = hostHeader;
    }

    public String getSecurityGroupName() {
        return securityGroupName;
    }

    public void setSecurityGroupName(String securityGroupName) {
        this.securityGroupName = securityGroupName;
    }

    public String getTagKey() {
        return tagKey;
    }

    public void setTagKey(String tagKey) {
        this.tagKey = tagKey;
    }

    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }
}
