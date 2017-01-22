package com.github.theborakompanioni.openmrc.spring.web;

import com.fasterxml.jackson.databind.Module;
import com.github.theborakompanioni.openmrc.OpenMrcRequestInterceptor;
import com.github.theborakompanioni.openmrc.json.OpenMrcJsonMapper;
import com.github.theborakompanioni.openmrc.json.StandardOpenMrcJsonMapper;
import com.github.theborakompanioni.openmrc.spring.impl.LocaleRequestInterceptor;
import com.github.theborakompanioni.openmrc.spring.impl.ReferrerRequestInterceptor;
import com.github.theborakompanioni.openmrc.spring.impl.UserAgentRequestInterceptor;
import com.github.theborakompanioni.openmrc.spring.jackson.OpenMrcModule;
import com.github.theborakompanioni.openmrc.spring.mapper.OpenMrcHttpRequestMapper;
import com.github.theborakompanioni.openmrc.spring.mapper.OpenMrcJsonHttpRequestMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

public abstract class OpenMrcWebConfigurationSupport implements OpenMrcWebConfiguration {

    public OpenMrcJsonMapper openMrcJsonMapper() {
        return new StandardOpenMrcJsonMapper(extensionRegistry(), metricsRegistry());
    }

    public Module openMrcModule() {
        return new OpenMrcModule(openMrcJsonMapper());
    }

    @Override
    public OpenMrcHttpRequestMapper openMrcRequestMapper() {
        return new OpenMrcJsonHttpRequestMapper(openMrcJsonMapper());
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
