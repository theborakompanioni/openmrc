package org.tbk.openmrc.impl;

import org.tbk.openmrc.OpenMrcExtensions;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by void on 20.06.15.
 */
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
        super(OpenMrcExtensions.Locale.locale, Optional.of(UNKNOWN));
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
