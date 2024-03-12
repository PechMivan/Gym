package com.gym.gym.config;

import com.gym.gym.filters.TransactionIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<TransactionIdFilter> transactionIdFilterRegistration() {
        FilterRegistrationBean<TransactionIdFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new TransactionIdFilter());
        registration.addUrlPatterns("/gym/*");
        registration.setName("transactionIdFilter");
        registration.setOrder(1);
        return registration;
    }
}
