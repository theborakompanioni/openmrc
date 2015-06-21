package org.tbk.openmrc.impl;

import com.google.protobuf.GeneratedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcRequestInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by void on 21.06.15.
 */
public abstract class ExtensionHttpRequestInterceptorSupport<EXT extends GeneratedMessage> implements OpenMrcRequestInterceptor<HttpServletRequest> {

    private static final Logger log = LoggerFactory.getLogger(ExtensionHttpRequestInterceptorSupport.class);

    private final GeneratedMessage.GeneratedExtension<OpenMrc.Request, EXT> extension;
    private final Optional<EXT> defaultValue;

    public ExtensionHttpRequestInterceptorSupport(GeneratedMessage.GeneratedExtension<
            org.tbk.openmrc.OpenMrc.Request, EXT> extension) {
        this(extension, Optional.empty());
    }

    public ExtensionHttpRequestInterceptorSupport(GeneratedMessage.GeneratedExtension<
            org.tbk.openmrc.OpenMrc.Request, EXT> extension, Optional<EXT> defaultValue) {
        this.extension = extension;
        this.defaultValue = defaultValue;
    }

    public boolean hasDefaultValue() {
        return defaultValue.isPresent();
    }

    public EXT getDefaultValue() {
        return defaultValue.orElse(null);
    }

    @Override
    public OpenMrc.Request.Builder intercept(HttpServletRequest context, OpenMrc.Request.Builder builder) {
        if (builder.hasExtension(extension)) {
            return builder;
        }

        try {
            Optional<EXT> extractedValue = extract(context);
            if (extractedValue.isPresent()) {
                builder.setExtension(extension, extractedValue.get());
            } else if (defaultValue.isPresent()) {
                builder.setExtension(extension, defaultValue.get());
            }
        } catch (Exception e) {
            String extensionName = extension.getDescriptor().getFullName();
            log.warn("Exception while parsing extracting " + extensionName + " from HttpRequest", e);
        }

        return builder;
    }

    protected abstract Optional<EXT> extract(HttpServletRequest context);
}
