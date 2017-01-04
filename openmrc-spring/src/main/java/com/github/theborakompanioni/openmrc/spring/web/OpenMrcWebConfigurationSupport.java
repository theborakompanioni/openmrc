package com.github.theborakompanioni.openmrc.spring.web;

import com.github.theborakompanioni.openmrc.OpenMrcRequestInterceptor;
import com.github.theborakompanioni.openmrc.json.StandardOpenMrcJsonMapper;
import com.github.theborakompanioni.openmrc.spring.impl.LocaleRequestInterceptor;
import com.github.theborakompanioni.openmrc.spring.impl.ReferrerRequestInterceptor;
import com.github.theborakompanioni.openmrc.spring.impl.UserAgentRequestInterceptor;
import com.github.theborakompanioni.openmrc.spring.mapper.OpenMrcHttpRequestMapper;
import com.github.theborakompanioni.openmrc.spring.mapper.StandardOpenMrcJsonHttpRequestMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public abstract class OpenMrcWebConfigurationSupport implements OpenMrcWebConfiguration {

    @Override
    public OpenMrcHttpRequestMapper openMrcRequestMapper() {
        StandardOpenMrcJsonMapper standardOpenMrcJsonMapper = new StandardOpenMrcJsonMapper(extensionRegistry(), metricsRegistry());
        return new StandardOpenMrcJsonHttpRequestMapper(standardOpenMrcJsonMapper);
    }

    @Override
    public List<OpenMrcRequestInterceptor<HttpServletRequest>> openMrcRequestInterceptor() {
        return Arrays.asList(
                new UserAgentRequestInterceptor(),
                new ReferrerRequestInterceptor(),
                new LocaleRequestInterceptor()
        );
    }
}
