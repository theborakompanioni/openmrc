package com.github.theborakompanioni.openmrc.spring;

import com.codahale.metrics.MetricRegistry;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.github.theborakompanioni.openmrc.OpenMrcRequestInterceptor;
import com.github.theborakompanioni.openmrc.OpenMrcRequestService;
import com.github.theborakompanioni.openmrc.OpenMrcResponseSupplier;
import com.github.theborakompanioni.openmrc.json.OpenMrcJsonMapper;
import com.github.theborakompanioni.openmrc.spring.mapper.OpenMrcHttpRequestMapper;
import com.github.theborakompanioni.openmrc.spring.web.OpenMrcWebConfigurationSupport;
import com.google.common.collect.Lists;
import com.google.protobuf.ExtensionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Configuration
public abstract class SpringOpenMrcConfigurationSupport extends OpenMrcWebConfigurationSupport {

    private static final Logger log = LoggerFactory.getLogger(SpringOpenMrcConfigurationSupport.class);

    @Autowired(required = false)
    private List<OpenMrcRequestConsumer> requestConsumers = Collections.emptyList();

    @Autowired(required = false)
    private List<OpenMrcRequestInterceptor<HttpServletRequest>> requestInterceptors = Collections.emptyList();

    @Override
    @Bean
    public abstract OpenMrcResponseSupplier openMrcResponseSupplier();

    @Override
    @Bean
    public MetricRegistry metricsRegistry() {
        return super.metricsRegistry();
    }

    @Override
    @Bean
    public ExtensionRegistry extensionRegistry() {
        return super.extensionRegistry();
    }

    @Override
    @Bean
    public OpenMrcHttpRequestMapper openMrcRequestMapper() {
        return super.openMrcRequestMapper();
    }

    @Override
    @Bean
    public OpenMrcJsonMapper openMrcJsonMapper() {
        return super.openMrcJsonMapper();
    }

    @Override
    @Bean
    public OpenMrcRequestService<HttpServletRequest, ResponseEntity<String>> openMrcRequestService() {
        return super.openMrcRequestService();
    }

    @Override
    @Bean
    public List<OpenMrcRequestConsumer> openMrcRequestConsumer() {
        List<OpenMrcRequestConsumer> consumers = Lists.newArrayList(this.requestConsumers);

        if (consumers.isEmpty()) {
            log.warn("No request consumer found. Registering standard consumers.");
            consumers.addAll(super.openMrcRequestConsumer());
        }

        log.info("registering {} request consumer(s): {}", consumers.size(), consumers);
        return consumers;
    }

    @Override
    @Bean
    public List<OpenMrcRequestInterceptor<HttpServletRequest>> openMrcRequestInterceptor() {
        List<OpenMrcRequestInterceptor<HttpServletRequest>> interceptors = Lists.newArrayList(this.requestInterceptors);

        if (interceptors.isEmpty()) {
            log.warn("No request interceptors found. Registering standard interceptors.");
            interceptors.addAll(super.openMrcRequestInterceptor());
        }

        log.info("registering {} request interceptor(s): {}", interceptors.size(), interceptors);
        return interceptors;
    }
}
