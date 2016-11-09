package com.github.theborakompanioni.openmrc.impl;

import com.github.theborakompanioni.openmrc.OpenMrcExtensions;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

public class LocaleRequestInterceptor extends ExtensionHttpRequestInterceptorSupport<OpenMrcExtensions.Locale> {

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
    protected Optional<OpenMrcExtensions.Locale> extract(HttpServletRequest context) {
        Optional<Locale> locale = Optional.ofNullable(context.getLocale());

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
