package org.tbk.openmrc;

/**
 * Created by void on 20.06.15.
 */
public interface OpenMrcRequestInterceptor<T> {
    OpenMrc.Request.Builder intercept(T context, OpenMrc.Request.Builder builder);
}
