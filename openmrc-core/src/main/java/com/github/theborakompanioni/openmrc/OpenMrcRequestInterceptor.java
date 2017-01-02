package com.github.theborakompanioni.openmrc;

import io.reactivex.Observable;

public interface OpenMrcRequestInterceptor<T> {
    Observable<OpenMrc.Request.Builder> intercept(T context, OpenMrc.Request.Builder builder);
}
