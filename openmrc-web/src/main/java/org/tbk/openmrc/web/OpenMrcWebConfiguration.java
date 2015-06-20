package org.tbk.openmrc.web;

import com.codahale.metrics.MetricRegistry;
import com.google.protobuf.ExtensionRegistry;
import org.tbk.openmrc.OpenMrc;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by void on 20.06.15.
 */
public interface OpenMrcWebConfiguration {
    MetricRegistry metricsRegistry();

    ExtensionRegistry extensionRegistry();

    OpenMrcHttpRequestMapper httpRequestMapper();

    OpenMrcHttpRequestService httpRequestService();

    List<Consumer<OpenMrc.Request>> openMrcRequestConsumer();

    List<OpenMrcRequestInterceptor<HttpServletRequest>> httpRequestInterceptor();
}
