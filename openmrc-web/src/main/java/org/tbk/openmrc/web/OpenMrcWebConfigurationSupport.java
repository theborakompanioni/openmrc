package org.tbk.openmrc.web;

import com.codahale.metrics.MetricRegistry;
import com.google.protobuf.ExtensionRegistry;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.mapper.StandardOpenMrcJsonMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by void on 20.06.15.
 */
public abstract class OpenMrcWebConfigurationSupport implements OpenMrcWebConfiguration {
    @Override
    public MetricRegistry metricsRegistry() {
        return new MetricRegistry();
    }

    @Override
    public ExtensionRegistry extensionRegistry() {
        return ExtensionRegistry.newInstance();
    }

    @Override
    public OpenMrcHttpRequestMapper httpRequestMapper() {
        StandardOpenMrcJsonMapper standardOpenMrcJsonMapper = new StandardOpenMrcJsonMapper(extensionRegistry(), metricsRegistry());
        return new OpenMrcJsonHttpRequestMapper(standardOpenMrcJsonMapper, httpRequestInterceptor());
    }

    @Override
    public OpenMrcHttpRequestService httpRequestService() {
        return new OpenMrcHttpRequestService(httpRequestMapper(), openMrcRequestConsumer());
    }

    @Override
    public List<Consumer<OpenMrc.Request>> openMrcRequestConsumer() {
        return Collections.emptyList();
    }

    @Override
    public List<OpenMrcRequestInterceptor<HttpServletRequest>> httpRequestInterceptor() {
        return Collections.emptyList();
    }
}
