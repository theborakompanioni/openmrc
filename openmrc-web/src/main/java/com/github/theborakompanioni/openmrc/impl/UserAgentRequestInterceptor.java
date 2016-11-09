package com.github.theborakompanioni.openmrc.impl;

import com.github.theborakompanioni.openmrc.OpenMrcExtensions;
import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class UserAgentRequestInterceptor extends ExtensionHttpRequestInterceptorSupport<OpenMrcExtensions.UserAgent> {

    private static final OpenMrcExtensions.UserAgent UNKNOWN = OpenMrcExtensions.UserAgent.newBuilder()
            .setValue("?")
            .build();

    private static final String HTTP_HEADER_USER_AGENT = "User-Agent";

    public UserAgentRequestInterceptor() {
        super(OpenMrcExtensions.UserAgent.userAgent, Optional.of(UNKNOWN));
    }

    @Override
    protected Optional<OpenMrcExtensions.UserAgent> extract(HttpServletRequest context) {
        Optional<String> userAgent = Optional.ofNullable(context.getHeader(HTTP_HEADER_USER_AGENT))
                .map(Strings::emptyToNull);

        return userAgent.map(val -> OpenMrcExtensions.UserAgent.newBuilder()
                .setValue(val)
                .build());
    }

}
