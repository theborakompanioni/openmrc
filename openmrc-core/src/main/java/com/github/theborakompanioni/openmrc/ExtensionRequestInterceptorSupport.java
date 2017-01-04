package com.github.theborakompanioni.openmrc;

import com.google.protobuf.GeneratedMessage;
import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public abstract class ExtensionRequestInterceptorSupport<T, EXT extends GeneratedMessage> implements OpenMrcRequestInterceptor<T> {

    private static final Logger log = LoggerFactory.getLogger(ExtensionRequestInterceptorSupport.class);

    private final GeneratedMessage.GeneratedExtension<OpenMrc.Request, EXT> extension;
    private final Optional<EXT> defaultValue;

    public ExtensionRequestInterceptorSupport(GeneratedMessage.GeneratedExtension<OpenMrc.Request, EXT> extension) {
        this(extension, Optional.empty());
    }

    public ExtensionRequestInterceptorSupport(GeneratedMessage.GeneratedExtension<OpenMrc.Request, EXT> extension, Optional<EXT> defaultValue) {
        this.extension = requireNonNull(extension);
        this.defaultValue = requireNonNull(defaultValue);
    }

    public boolean hasDefaultValue() {
        return defaultValue.isPresent();
    }

    public Optional<EXT> getDefaultValue() {
        return defaultValue;
    }

    private Observable<EXT> getDefaultValueObservable() {
        return Observable.defer(() -> getDefaultValue()
                .map(Observable::just)
                .orElseGet(Observable::empty));
    }

    @Override
    public Observable<OpenMrc.Request.Builder> intercept(T context, OpenMrc.Request.Builder builder) {
        if (builder.hasExtension(extension)) {
            return Observable.just(builder);
        }

        return Observable.defer(() -> {
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
        });
    }

    protected abstract Observable<EXT> extract(T context);
}
