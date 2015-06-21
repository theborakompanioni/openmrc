package org.tbk.openmrc.impl;

import com.google.common.base.Strings;
import com.google.protobuf.GeneratedMessage;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcExtensions;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by void on 20.06.15.
 */
public class ReferrerRequestInterceptor extends ExtensionHttpRequestInterceptorSupport<OpenMrcExtensions.Referrer> {

    private static final OpenMrcExtensions.Referrer UNKNOWN = OpenMrcExtensions.Referrer.newBuilder()
            .setValue("?")
            .build();

    public ReferrerRequestInterceptor() {
        super(OpenMrcExtensions.Referrer.referrer, Optional.of(UNKNOWN));
    }

    @Override
    protected Optional<OpenMrcExtensions.Referrer> extract(HttpServletRequest context) {

        Optional<String> userAgent = Optional.ofNullable(Strings.emptyToNull(context.getHeader("Referer")));

        return userAgent.map(val -> OpenMrcExtensions.Referrer.newBuilder()
                .setValue(val)
                .build());
    }
}
