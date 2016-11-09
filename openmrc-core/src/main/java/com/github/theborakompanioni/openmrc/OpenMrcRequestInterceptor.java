package com.github.theborakompanioni.openmrc;

public interface OpenMrcRequestInterceptor<T> {
    OpenMrc.Request.Builder intercept(T context, OpenMrc.Request.Builder builder);
}
