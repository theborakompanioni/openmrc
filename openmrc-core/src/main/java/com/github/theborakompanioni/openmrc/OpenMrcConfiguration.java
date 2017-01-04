package com.github.theborakompanioni.openmrc;

import com.codahale.metrics.MetricRegistry;
import com.github.theborakompanioni.openmrc.mapper.OpenMrcMapper;
import com.google.protobuf.ExtensionRegistry;

import java.util.List;

public interface OpenMrcConfiguration<Req, Res> {
    MetricRegistry metricsRegistry();

    ExtensionRegistry extensionRegistry();

    OpenMrcMapper<Req, Res, Req, Res> openMrcRequestMapper();

    OpenMrcRequestService<Req, Res> openMrcRequestService();

    OpenMrcResponseSupplier openMrcResponseSupplier();

    List<OpenMrcRequestConsumer> openMrcRequestConsumer();

    List<OpenMrcRequestInterceptor<Req>> openMrcRequestInterceptor();
}
