package com.coder.log.annotation;

import com.coder.log.config.LogSwitchConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(LogSwitchConfiguration.class)
public @interface EnableLog {
}
