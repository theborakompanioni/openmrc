package org.tbk.openmrc.impl;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcExtensions;
import org.tbk.openmrc.OpenMrcRequestInterceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by void on 20.06.15.
 */
public class OperatingSystemRequestInterceptor implements OpenMrcRequestInterceptor<HttpServletRequest> {

    private static final Logger log = LoggerFactory.getLogger(OperatingSystemRequestInterceptor.class);

    private static final OpenMrcExtensions.OperatingSystem UNKNOWN = OpenMrcExtensions.OperatingSystem.newBuilder()
            .setManufacturer("?")
            .setName("?")
            .setVersion("?")
            .build();

    @Override
    public OpenMrc.Request.Builder intercept(HttpServletRequest context, OpenMrc.Request.Builder builder) {
        if (builder.hasExtension(OpenMrcExtensions.OperatingSystem.operatingSystem)) {
            return builder;
        }

        try {
            String userAgentHeaderValue = Strings.nullToEmpty(context.getHeader("User-Agent"));
            OpenMrcExtensions.OperatingSystem operatingSystem = extractOperatingSystem(userAgentHeaderValue);

            builder.setExtension(OpenMrcExtensions.OperatingSystem.operatingSystem, operatingSystem);
        } catch (Exception e) {
            log.warn("Exeption while parsing UserAgent as OpenMrc.Request", e);
        }

        return builder;
    }

    private OpenMrcExtensions.OperatingSystem extractOperatingSystem(String userAgent) {
        if (userAgent.isEmpty()) {
            return UNKNOWN;
        }
        return UNKNOWN;
    }
}
