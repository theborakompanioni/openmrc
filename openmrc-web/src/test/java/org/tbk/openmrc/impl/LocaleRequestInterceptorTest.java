package org.tbk.openmrc.impl;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcExtensions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by void on 21.06.15.
 */
public class LocaleRequestInterceptorTest {

    MockHttpServletRequest withoutLocale;
    MockHttpServletRequest withLocale;
    LocaleRequestInterceptor localeRequestInterceptor;
    OpenMrc.Request.Builder openMrcRequestBuilder;

    @Before
    public void setup() throws Exception {
        this.withoutLocale = new MockHttpServletRequest();
        ArrayList<Locale> listContainingNull = Lists.newArrayList();
        listContainingNull.add(null);
        this.withoutLocale.setPreferredLocales(listContainingNull);

        this.withLocale = new MockHttpServletRequest();
        withLocale.setPreferredLocales(Arrays.asList(Locale.JAPAN));

        this.localeRequestInterceptor = new LocaleRequestInterceptor();
        this.openMrcRequestBuilder = OpenMrc.Request.newBuilder();
    }

    @Test
    public void itShouldReturnEmptyOnRequestWithoutLocaleWithoutDefaultValue() throws Exception {
        this.localeRequestInterceptor = new LocaleRequestInterceptor(Optional.empty());
        assertThat(localeRequestInterceptor.hasDefaultValue(), is(false));

        assertThat(localeRequestInterceptor.extract(withoutLocale), is(notNullValue()));
        assertThat(localeRequestInterceptor.extract(withoutLocale).isPresent(), is(false));

        OpenMrc.Request.Builder requestBuilder = localeRequestInterceptor.intercept(withoutLocale, openMrcRequestBuilder);
        assertThat(requestBuilder, is(notNullValue()));
        assertThat(requestBuilder.hasExtension(OpenMrcExtensions.Locale.locale), is(false));
    }

    @Test
    public void itShouldReturnDefaultValueOnRequestWithoutLocale() throws Exception {
        assertThat(localeRequestInterceptor.hasDefaultValue(), is(true));

        assertThat(localeRequestInterceptor.extract(withoutLocale), is(notNullValue()));
        assertThat(localeRequestInterceptor.extract(withoutLocale).isPresent(), is(false));

        OpenMrc.Request.Builder requestBuilder = localeRequestInterceptor.intercept(withoutLocale, openMrcRequestBuilder);
        assertThat(requestBuilder, is(notNullValue()));
        assertThat(requestBuilder.hasExtension(OpenMrcExtensions.Locale.locale), is(true));
        assertThat(requestBuilder.getExtension(OpenMrcExtensions.Locale.locale).getCountry(), is(localeRequestInterceptor.getDefaultValue().getCountry()));
    }

    @Test
    public void itShouldReturnLocaleOnRequestWithLocale() throws Exception {
        assertThat(localeRequestInterceptor.extract(withLocale), is(notNullValue()));
        assertThat(localeRequestInterceptor.extract(withLocale).isPresent(), is(true));
        assertThat(localeRequestInterceptor.extract(withLocale).get().getCountry(), is(Locale.JAPAN.getCountry()));
        assertThat(localeRequestInterceptor.extract(withLocale).get().getLanguage(), is(Locale.JAPAN.getLanguage()));

        OpenMrc.Request.Builder requestBuilder = localeRequestInterceptor.intercept(withLocale, openMrcRequestBuilder);
        assertThat(requestBuilder, is(notNullValue()));
        assertThat(requestBuilder.hasExtension(OpenMrcExtensions.Locale.locale), is(true));
        assertThat(requestBuilder.getExtension(OpenMrcExtensions.Locale.locale).getCountry(), is(Locale.JAPAN.getCountry()));
    }
}