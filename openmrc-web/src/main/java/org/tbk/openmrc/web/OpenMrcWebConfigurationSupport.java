package org.tbk.openmrc.web;

import com.codahale.metrics.MetricRegistry;
import com.google.protobuf.ExtensionRegistry;
import org.tbk.openmrc.LoggingRequestConsumer;
import org.tbk.openmrc.OpenMrcExtensions;
import org.tbk.openmrc.OpenMrcRequestConsumer;
import org.tbk.openmrc.OpenMrcRequestInterceptor;
import org.tbk.openmrc.impl.LocaleRequestInterceptor;
import org.tbk.openmrc.impl.ReferrerRequestInterceptor;
import org.tbk.openmrc.impl.UserAgentRequestInterceptor;
import org.tbk.openmrc.mapper.OpenMrcHttpRequestMapper;
import org.tbk.openmrc.mapper.StandardOpenMrcJsonHttpRequestMapper;
import org.tbk.openmrc.mapper.StandardOpenMrcJsonMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

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
        return Arrays.asList(new LoggingRequestConsumer());
    }

    @Override
    public List<OpenMrcRequestInterceptor<HttpServletRequest>> httpRequestInterceptor() {
        return Arrays.asList(
                new UserAgentRequestInterceptor(),
                new ReferrerRequestInterceptor(),
                new LocaleRequestInterceptor()
        );
    }
}
