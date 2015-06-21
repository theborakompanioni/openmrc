package org.tbk.openmrc.impl;

import com.google.common.base.Strings;
import org.tbk.openmrc.OpenMrcExtensions;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by void on 20.06.15.
 */
public class UserAgentRequestInterceptor extends ExtensionHttpRequestInterceptorSupport<OpenMrcExtensions.UserAgent> {

    private static final OpenMrcExtensions.UserAgent UNKNOWN = OpenMrcExtensions.UserAgent.newBuilder()
            .setValue("?")
            .build();

    public UserAgentRequestInterceptor() {
        super(OpenMrcExtensions.UserAgent.userAgent, Optional.of(UNKNOWN));
    }

    @Override
    protected Optional<OpenMrcExtensions.UserAgent> extract(HttpServletRequest context) {
        Optional<String> userAgent = Optional.ofNullable(Strings.emptyToNull(context.getHeader("User-Agent")));

        return userAgent.map(val -> OpenMrcExtensions.UserAgent.newBuilder()
                .setValue(val)
                .build());
    }

}
