package com.aduro.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class LocalValidatorFactoryBeanConfiguration {

    @Bean
    LocalValidatorFactoryBean localValidatorFactoryBean() {
        return new LocalValidatorFactoryBean ();
    }
}
