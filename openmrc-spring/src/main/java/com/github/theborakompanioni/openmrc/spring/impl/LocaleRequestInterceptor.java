package com.github.theborakompanioni.openmrc.spring.impl;

import com.github.theborakompanioni.openmrc.ExtensionRequestInterceptorSupport;
import com.github.theborakompanioni.openmrc.OpenMrcExtensions;
import io.reactivex.Observable;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

public class LocaleRequestInterceptor extends ExtensionRequestInterceptorSupport<HttpServletRequest, OpenMrcExtensions.Locale> {

    private static final OpenMrcExtensions.Locale UNKNOWN = OpenMrcExtensions.Locale.newBuilder()
            .setCountry("?")
            .setDisplayCountry("?")
            .setDisplayLanguage("?")
            .setDisplayName("?")
            .setLanguage("?")
            .setLanguageTag("?")
            .setValue("?")
            .build();

    public LocaleRequestInterceptor() {
        this(Optional.of(UNKNOWN));
    }

    public LocaleRequestInterceptor(Optional<OpenMrcExtensions.Locale> defaultValue) {
        super(OpenMrcExtensions.Locale.locale, defaultValue);
    }

    @Override
    protected Observable<OpenMrcExtensions.Locale> extract(HttpServletRequest context) {
        Observable<Locale> locale = Optional.ofNullable(context.getLocale())
                .map(Observable::just)
                .orElse(Observable.empty());

        return locale.map(val -> OpenMrcExtensions.Locale.newBuilder()
                .setCountry(val.getCountry())
                .setDisplayCountry(val.getDisplayCountry())
                .setDisplayLanguage(val.getDisplayLanguage())
                .setDisplayName(val.getDisplayName())
                .setLanguage(val.getLanguage())
                .setLanguageTag(val.getISO3Language())
                .setValue(val.toString())
                .build());
    }

}
