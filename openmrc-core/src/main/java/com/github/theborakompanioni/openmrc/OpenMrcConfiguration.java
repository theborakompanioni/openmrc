package com.github.theborakompanioni.openmrc;

import com.codahale.metrics.MetricRegistry;
import com.google.protobuf.ExtensionRegistry;

import java.util.Collections;
import java.util.List;

public interface OpenMrcConfiguration<Req, Res> {

    OpenMrcMapper<Req, Res, Req, Res> openMrcRequestMapper();

    OpenMrcResponseSupplier openMrcResponseSupplier();

    default MetricRegistry metricsRegistry() {
        return new MetricRegistry();
    }

    default ExtensionRegistry extensionRegistry() {
        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(OpenMrcExtensions.Browser.browser);
        registry.add(OpenMrcExtensions.OperatingSystem.operatingSystem);
        registry.add(OpenMrcExtensions.UserAgent.userAgent);
        registry.add(OpenMrcExtensions.Locale.locale);
        registry.add(OpenMrcExtensions.Referrer.referrer);
        return registry;
    }

    @SuppressWarnings("unchecked")
    default OpenMrcRequestService<Req, Res> openMrcRequestService() {
        return new OpenMrcRequestServiceImpl(
                openMrcRequestMapper(),
                openMrcResponseSupplier(),
                openMrcRequestInterceptor(),
                openMrcRequestConsumer());
    }

    default List<OpenMrcRequestConsumer> openMrcRequestConsumer() {
        return Collections.singletonList(new LoggingRequestConsumer());
    }

    default List<OpenMrcRequestInterceptor<Req>> openMrcRequestInterceptor() {
        return Collections.emptyList();
    }
}
