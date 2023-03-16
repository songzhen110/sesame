package com.coder.log.config;

import com.coder.log.aspect.LogAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnBean(LogSwitchConfiguration.class)
@EnableConfigurationProperties(LogProperties.class)
public class LogAutoconfiguration {

    public LogAspect logAspect(@Autowired LogProperties logProperties){
        return new LogAspect(logProperties);
    }
}
