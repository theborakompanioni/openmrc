package com.github.theborakompanioni.openmrc.spring.impl;

import com.github.theborakompanioni.openmrc.ExtensionRequestInterceptorSupport;
import com.github.theborakompanioni.openmrc.OpenMrcExtensions;
import com.google.common.base.Strings;
import io.reactivex.Observable;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ReferrerRequestInterceptor extends ExtensionRequestInterceptorSupport<HttpServletRequest, OpenMrcExtensions.Referrer> {

    private static final OpenMrcExtensions.Referrer UNKNOWN = OpenMrcExtensions.Referrer.newBuilder()
            .setValue("?")
            .build();
    private static final String HTTP_HEADER_REFERER = "Referer";

    public ReferrerRequestInterceptor() {
        super(OpenMrcExtensions.Referrer.referrer, Optional.of(UNKNOWN));
    }

    @Override
    protected Observable<OpenMrcExtensions.Referrer> extract(HttpServletRequest context) {
        Observable<String> referrer = Optional.ofNullable(context.getHeader(HTTP_HEADER_REFERER))
                .map(Strings::emptyToNull)
                .map(Observable::just)
                .orElse(Observable.empty());

        return referrer.map(val -> OpenMrcExtensions.Referrer.newBuilder()
                .setValue(val)
                .build());
    }
}
