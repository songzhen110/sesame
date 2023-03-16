package com.coder.log.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "api.log.config")
public class LogProperties {
    private String prefix;
    private String suffix;
}
