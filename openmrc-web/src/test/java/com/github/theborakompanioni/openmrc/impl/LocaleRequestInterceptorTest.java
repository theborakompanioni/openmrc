package com.github.theborakompanioni.openmrc.impl;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcExtensions;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class LocaleRequestInterceptorTest {

    private MockHttpServletRequest withoutLocale;
    private MockHttpServletRequest withLocale;
    private LocaleRequestInterceptor localeRequestInterceptor;
    private OpenMrc.Request.Builder openMrcRequestBuilder;

    @Before
    public void setup() throws Exception {
        this.withoutLocale = new MockHttpServletRequest();
        ArrayList<Locale> listContainingNull = Lists.newArrayList();
        listContainingNull.add(null);
        this.withoutLocale.setPreferredLocales(listContainingNull);

        this.withLocale = new MockHttpServletRequest();
        withLocale.setPreferredLocales(Collections.singletonList(Locale.JAPAN));

        this.localeRequestInterceptor = new LocaleRequestInterceptor();
        this.openMrcRequestBuilder = OpenMrc.Request.newBuilder();
    }

    @Test
    public void itShouldReturnEmptyOnRequestWithoutLocaleWithoutDefaultValue() throws Exception {
        this.localeRequestInterceptor = new LocaleRequestInterceptor(Optional.empty());
        assertThat(localeRequestInterceptor.hasDefaultValue(), is(false));

        assertThat(localeRequestInterceptor.extract(withoutLocale), is(notNullValue()));
        assertThat(localeRequestInterceptor.extract(withoutLocale).isEmpty().blockingGet(), is(true));

        OpenMrc.Request.Builder requestBuilder = localeRequestInterceptor.intercept(withoutLocale, openMrcRequestBuilder)
                .blockingSingle();
        assertThat(requestBuilder, is(notNullValue()));
        assertThat(requestBuilder.hasExtension(OpenMrcExtensions.Locale.locale), is(false));
    }

    @Test
    public void itShouldReturnDefaultValueOnRequestWithoutLocale() throws Exception {
        assertThat(localeRequestInterceptor.hasDefaultValue(), is(true));

        assertThat(localeRequestInterceptor.extract(withoutLocale), is(notNullValue()));
        assertThat(localeRequestInterceptor.extract(withoutLocale).isEmpty().blockingGet(), is(true));

        OpenMrc.Request.Builder requestBuilder = localeRequestInterceptor.intercept(withoutLocale, openMrcRequestBuilder)
                .blockingSingle();
        assertThat(requestBuilder, is(notNullValue()));
        assertThat(requestBuilder.hasExtension(OpenMrcExtensions.Locale.locale), is(true));
        assertThat(requestBuilder.getExtension(OpenMrcExtensions.Locale.locale).getCountry(), is(localeRequestInterceptor.getDefaultValue().getCountry()));
    }

    @Test
    public void itShouldReturnLocaleOnRequestWithLocale() throws Exception {
        assertThat(localeRequestInterceptor.extract(withLocale), is(notNullValue()));
        assertThat(localeRequestInterceptor.extract(withLocale).isEmpty().blockingGet(), is(false));
        assertThat(localeRequestInterceptor.extract(withLocale).blockingSingle().getCountry(), is(Locale.JAPAN.getCountry()));
        assertThat(localeRequestInterceptor.extract(withLocale).blockingSingle().getLanguage(), is(Locale.JAPAN.getLanguage()));

        OpenMrc.Request.Builder requestBuilder = localeRequestInterceptor.intercept(withLocale, openMrcRequestBuilder)
                .blockingSingle();
        assertThat(requestBuilder, is(notNullValue()));
        assertThat(requestBuilder.hasExtension(OpenMrcExtensions.Locale.locale), is(true));
        assertThat(requestBuilder.getExtension(OpenMrcExtensions.Locale.locale).getCountry(), is(Locale.JAPAN.getCountry()));
    }
}
