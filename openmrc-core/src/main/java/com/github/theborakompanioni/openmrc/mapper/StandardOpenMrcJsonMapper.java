package com.github.theborakompanioni.openmrc.mapper;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.util.OpenMrcValidator;
import com.github.theborakompanioni.openmrc.util.OpenMrcValidator.OpenMrcValidationException;
import com.google.protobuf.ExtensionRegistry;
import com.googlecode.protobuf.format.JsonFormat;

import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

public class StandardOpenMrcJsonMapper implements OpenMrcMapper<String, String, String, String> {

    private final ExtensionRegistry extensionRegistry;
    private final OpenMrcValidator validator;

    private final Meter invalidRequests;
    private final Meter validRequests;
    private final JsonFormat jsonFormat;

    public StandardOpenMrcJsonMapper(ExtensionRegistry extensionRegistry, MetricRegistry metricRegistry) {
        this.extensionRegistry = requireNonNull(extensionRegistry);
        this.invalidRequests = requireNonNull(metricRegistry.meter("openmrc.request.invalid"));
        this.validRequests = requireNonNull(metricRegistry.meter("openmrc.request.valid"));

        this.validator = new OpenMrcValidator();
        this.jsonFormat = new JsonFormat();
    }

    @Override
    public String toExchangeRequest(@Nullable OpenMrc.Request request) {
        try {
            return jsonFormat.printToString(request);
        } catch (Exception e) {
            throw new OpenMrcMappingException(e);
        }
    }

    @Override
    public String toExchangeResponse(@Nullable OpenMrc.Request request, OpenMrc.Response response) {
        try {
            return jsonFormat.printToString(response);
        } catch (Exception e) {
            throw new OpenMrcMappingException(e);
        }
    }

    @Override
    public OpenMrc.Request.Builder toOpenMrcRequest(String request) {
        OpenMrc.Request.Builder builder = OpenMrc.Request.newBuilder();

        try {
            jsonFormat.merge(request, extensionRegistry, builder);
        } catch (Exception e) {
            throw new OpenMrcMappingException(e);
        }

        try {
            validator.validate(builder);
            validRequests.mark();
        } catch (OpenMrcValidationException e) {
            invalidRequests.mark();
            throw new OpenMrcMappingException(e);
        }

        return builder;
    }

    @Override
    public OpenMrc.Response.Builder toOpenMrcResponse(@Nullable String request, String response) {
        OpenMrc.Response.Builder builder = OpenMrc.Response.newBuilder();

        try {
            jsonFormat.merge(response, extensionRegistry, builder);
        } catch (Exception e) {
            throw new OpenMrcMappingException(e);
        }

        try {
            validator.validate(builder);
        } catch (OpenMrcValidationException e) {
            throw new OpenMrcMappingException(e);
        }

        return builder;
    }
}
