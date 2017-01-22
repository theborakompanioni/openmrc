package com.github.theborakompanioni.openmrc.spring;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MetricsConfiguration {
    @Bean
    @Primary
    public MetricRegistry metricsRegistry() {
        return SharedMetricRegistries.getOrCreate("openmrc");
    }
}