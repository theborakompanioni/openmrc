package com.github.theborakompanioni.openmrc.spring.impl;

import com.github.theborakompanioni.openmrc.ExtensionRequestInterceptorSupport;
import com.github.theborakompanioni.openmrc.OpenMrcExtensions;
import com.google.common.base.Strings;
import io.reactivex.Observable;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class UserAgentRequestInterceptor extends ExtensionRequestInterceptorSupport<HttpServletRequest, OpenMrcExtensions.UserAgent> {

    private static final OpenMrcExtensions.UserAgent UNKNOWN = OpenMrcExtensions.UserAgent.newBuilder()
            .setValue("?")
            .build();

    private static final String HTTP_HEADER_USER_AGENT = "User-Agent";

    public UserAgentRequestInterceptor() {
        super(OpenMrcExtensions.UserAgent.userAgent, Optional.of(UNKNOWN));
    }

    @Override
    protected Observable<OpenMrcExtensions.UserAgent> extract(HttpServletRequest context) {
        Observable<String> userAgent = Optional.ofNullable(context.getHeader(HTTP_HEADER_USER_AGENT))
                .map(Strings::emptyToNull)
                .map(Observable::just)
                .orElse(Observable.empty());

        return userAgent.map(val -> OpenMrcExtensions.UserAgent.newBuilder()
                .setValue(val)
                .build());
    }

}
