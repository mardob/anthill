package com.net.anthill.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "default")
@ConfigurationPropertiesScan
@Configuration
@Getter @Setter
public class SystemConfigurations {
    private String adminUsername;
    private String adminPassword;
}
