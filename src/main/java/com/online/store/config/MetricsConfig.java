package com.online.store.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MetricsConfig implements WebMvcConfigurer {

    @Autowired
    private MeterRegistry meterRegistry;

    @Bean
    public RequestTimingInterceptor requestTimingInterceptor() {
        return new RequestTimingInterceptor(meterRegistry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestTimingInterceptor());
    }
}
