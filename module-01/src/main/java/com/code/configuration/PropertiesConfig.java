package com.code.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@PropertySource(value = {
        "classpath:${spring.profiles.active}/service.properties",
        "classpath:messages.properties"
})
@Configuration
public class PropertiesConfig {
}