package com.github.theborakompanioni.openmrc.web;

import com.codahale.metrics.MetricRegistry;
import com.github.theborakompanioni.openmrc.LoggingRequestConsumer;
import com.github.theborakompanioni.openmrc.OpenMrcExtensions;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.github.theborakompanioni.openmrc.OpenMrcRequestInterceptor;
import com.github.theborakompanioni.openmrc.impl.LocaleRequestInterceptor;
import com.github.theborakompanioni.openmrc.impl.ReferrerRequestInterceptor;
import com.github.theborakompanioni.openmrc.impl.UserAgentRequestInterceptor;
import com.github.theborakompanioni.openmrc.mapper.OpenMrcHttpRequestMapper;
import com.github.theborakompanioni.openmrc.mapper.StandardOpenMrcJsonHttpRequestMapper;
import com.github.theborakompanioni.openmrc.mapper.StandardOpenMrcJsonMapper;
import com.google.protobuf.ExtensionRegistry;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class OpenMrcWebConfigurationSupport implements OpenMrcWebConfiguration {
    @Override
    public MetricRegistry metricsRegistry() {
        return new MetricRegistry();
    }

    @Override
    public ExtensionRegistry extensionRegistry() {
        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(OpenMrcExtensions.Browser.browser);
        registry.add(OpenMrcExtensions.OperatingSystem.operatingSystem);
        registry.add(OpenMrcExtensions.UserAgent.userAgent);
        registry.add(OpenMrcExtensions.Locale.locale);
        registry.add(OpenMrcExtensions.Referrer.referrer);
        return registry;
    }

    @Override
    public OpenMrcHttpRequestMapper httpRequestMapper() {
        StandardOpenMrcJsonMapper standardOpenMrcJsonMapper = new StandardOpenMrcJsonMapper(extensionRegistry(), metricsRegistry());
        return new StandardOpenMrcJsonHttpRequestMapper(standardOpenMrcJsonMapper, httpRequestInterceptor());
    }

    @Override
    public OpenMrcHttpRequestService httpRequestService() {
        return new OpenMrcHttpRequestService(httpRequestMapper(), openMrcRequestConsumer());
    }

    @Override
    public List<OpenMrcRequestConsumer> openMrcRequestConsumer() {
        return Collections.singletonList(loggingRequestConsumer());
    }

    @Override
    public List<OpenMrcRequestInterceptor<HttpServletRequest>> httpRequestInterceptor() {
        return Arrays.asList(
                new UserAgentRequestInterceptor(),
                new ReferrerRequestInterceptor(),
                new LocaleRequestInterceptor()
        );
    }

    private LoggingRequestConsumer loggingRequestConsumer() {
        return new LoggingRequestConsumer();
    }
}
