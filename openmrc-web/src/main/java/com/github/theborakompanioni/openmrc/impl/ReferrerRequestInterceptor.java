package com.github.theborakompanioni.openmrc.impl;

import com.github.theborakompanioni.openmrc.OpenMrcExtensions;
import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ReferrerRequestInterceptor extends ExtensionHttpRequestInterceptorSupport<OpenMrcExtensions.Referrer> {

    private static final OpenMrcExtensions.Referrer UNKNOWN = OpenMrcExtensions.Referrer.newBuilder()
            .setValue("?")
            .build();
    private static final String HTTP_HEADER_REFERER = "Referer";

    public ReferrerRequestInterceptor() {
        super(OpenMrcExtensions.Referrer.referrer, Optional.of(UNKNOWN));
    }

    @Override
    protected Optional<OpenMrcExtensions.Referrer> extract(HttpServletRequest context) {

        Optional<String> userAgent = Optional.ofNullable(Strings.emptyToNull(context.getHeader(HTTP_HEADER_REFERER)));

        return userAgent.map(val -> OpenMrcExtensions.Referrer.newBuilder()
                .setValue(val)
                .build());
    }
}
