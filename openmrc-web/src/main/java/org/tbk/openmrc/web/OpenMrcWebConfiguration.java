package org.tbk.openmrc.web;

import com.codahale.metrics.MetricRegistry;
import com.google.protobuf.ExtensionRegistry;
import org.tbk.openmrc.OpenMrcRequestConsumer;
import org.tbk.openmrc.OpenMrcRequestInterceptor;

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
