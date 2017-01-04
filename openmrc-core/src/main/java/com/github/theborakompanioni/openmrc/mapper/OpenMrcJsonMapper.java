package com.github.theborakompanioni.openmrc.mapper;

import com.github.theborakompanioni.openmrc.OpenMrc;
import io.reactivex.Observable;

import javax.annotation.Nullable;

/**
 * Converts between OpenMrc and json requests/response.
 */
public interface OpenMrcJsonMapper extends OpenMrcMapper<String, String, String, String> {


    @Override
    default Observable<String> toExchangeRequest(@Nullable OpenMrc.Request request) {
        return Observable.defer(() -> Observable.just(toJson(request)));
    }

    @Override
    default Observable<String> toExchangeResponse(String originalRequest, @Nullable OpenMrc.Request request, OpenMrc.Response response) {
        return  Observable.defer(() -> Observable.just(toJson(request, response)));
    }

    @Override
    default Observable<OpenMrc.Request.Builder> toOpenMrcRequest(String request) {
        return  Observable.defer(() -> Observable.just(fromJsonAsRequest(request)));
    }

    @Override
    default Observable<OpenMrc.Response.Builder> toOpenMrcResponse(@Nullable String request, String response) {
        return  Observable.defer(() -> Observable.just(fromJsonAsResponse(response)));
    }

    /**
     * Converts an OpenMrc request to json.
     *
     * @param request OpenMrc request
     * @return Request in json format
     */
    String toJson(@Nullable OpenMrc.Request request) throws OpenMrcMappingException;

    /**
     * Converts an OpenMrc response to json format.
     *
     * @param response OpenMrc response
     * @return Response in json format
     */
    default String toJson(OpenMrc.Response response) throws OpenMrcMappingException {
        return toJson(null, response);
    }

    /**
     * Converts an OpenMrc response to json format.
     *
     * @param request  OpenMrc request, if necessary for context or validations
     * @param response OpenMrc response
     * @return Response in json format
     */
    String toJson(@Nullable OpenMrc.Request request, OpenMrc.Response response) throws OpenMrcMappingException;

    /**
     * Converts an exchange-specific request to OpenMrc.
     *
     * @param request Request in json format
     * @return OpenMrc request
     */
    OpenMrc.Request.Builder fromJsonAsRequest(String request) throws OpenMrcMappingException;

    /**
     * Converts a n exchange-specific response to OpenMrc.
     *
     * @param response The response
     * @return OpenMrc response
     */
    OpenMrc.Response.Builder fromJsonAsResponse(String response) throws OpenMrcMappingException;

}
