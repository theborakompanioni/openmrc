package com.github.theborakompanioni.openmrc.impl;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcRequestInterceptor;
import com.google.protobuf.GeneratedMessage;
import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

public abstract class ExtensionHttpRequestInterceptorSupport<EXT extends GeneratedMessage> implements OpenMrcRequestInterceptor<HttpServletRequest> {

    private static final Logger log = LoggerFactory.getLogger(ExtensionHttpRequestInterceptorSupport.class);

    private final GeneratedMessage.GeneratedExtension<OpenMrc.Request, EXT> extension;
    private final Optional<EXT> defaultValue;

    public ExtensionHttpRequestInterceptorSupport(GeneratedMessage.GeneratedExtension<OpenMrc.Request, EXT> extension) {
        this(extension, Optional.empty());
    }

    public ExtensionHttpRequestInterceptorSupport(GeneratedMessage.GeneratedExtension<OpenMrc.Request, EXT> extension, Optional<EXT> defaultValue) {
        this.extension = requireNonNull(extension);
        this.defaultValue = requireNonNull(defaultValue);
    }

    public boolean hasDefaultValue() {
        return defaultValue.isPresent();
    }

    public EXT getDefaultValue() {
        return defaultValue.orElse(null);
    }

    private Observable<EXT> getDefaultValueObservable() {
        return Observable.defer(() -> {
            if (hasDefaultValue()) {
                return Observable.just(getDefaultValue());
            } else {
                return Observable.empty();
            }
        });
    }

    @Override
    public Observable<OpenMrc.Request.Builder> intercept(HttpServletRequest context, OpenMrc.Request.Builder builder) {
        if (builder.hasExtension(extension)) {
            return Observable.just(builder);
        }

        return Observable.defer(() -> {
            try {
                final Observable<Optional<EXT>> extractedValue = extract(context)
                        .switchIfEmpty(getDefaultValueObservable())
                        .doOnError(e -> {
                            String extensionName = extension.getDescriptor().getFullName();
                            log.warn("Exception while extracting " + extensionName + " from HttpRequest", e);
                        })
                        .onErrorResumeNext(Observable.empty())
                        .map(Optional::ofNullable);

                return extractedValue.map(val -> {
                    if (val.isPresent()) {
                        builder.setExtension(extension, val.get());
                    }
                    return builder;
                }).switchIfEmpty(Observable.just(builder));
            } catch (Exception e) {

            }
            return Observable.just(builder);
        });
    }

    protected abstract Observable<EXT> extract(HttpServletRequest context);
}
