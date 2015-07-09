package com.github.theborakompanioni.openmrc.web;

import com.codahale.metrics.MetricRegistry;
import com.google.protobuf.ExtensionRegistry;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.github.theborakompanioni.openmrc.OpenMrcRequestInterceptor;
import com.github.theborakompanioni.openmrc.mapper.OpenMrcHttpRequestMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by void on 20.06.15.
 */
public interface OpenMrcWebConfiguration {
    MetricRegistry metricsRegistry();

    ExtensionRegistry extensionRegistry();

    OpenMrcHttpRequestMapper httpRequestMapper();

    OpenMrcHttpRequestService httpRequestService();

    List<OpenMrcRequestConsumer> openMrcRequestConsumer();

    List<OpenMrcRequestInterceptor<HttpServletRequest>> httpRequestInterceptor();
}
