package com.github.theborakompanioni.openmrc.mapper;

import com.codahale.metrics.MetricRegistry;
import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.util.OpenMrcValidator;
import com.google.protobuf.ExtensionRegistry;
import com.googlecode.protobuf.format.JsonFormat;

import javax.annotation.Nullable;

public class StandardOpenMrcJsonMapper implements OpenMrcMapper<String, String, String, String> {

    private ExtensionRegistry extensionRegistry;
    private OpenMrcValidator validator;

    public StandardOpenMrcJsonMapper(ExtensionRegistry extensionRegistry, MetricRegistry metricRegistry) {
        this.extensionRegistry = extensionRegistry;
        this.validator = new OpenMrcValidator(metricRegistry);
    }

    @Override
    public String toExchangeRequest(@Nullable OpenMrc.Request request) {
        try {
            return JsonFormat.printToString(request);
        } catch (Exception e) {
            throw new OpenMrcMappingException(e);
        }
    }

    @Override
    public String toExchangeResponse(@Nullable OpenMrc.Request request, OpenMrc.Response response) {
        try {
            return JsonFormat.printToString(response);
        } catch (Exception e) {
            throw new OpenMrcMappingException(e);
        }
    }

    @Override
    public OpenMrc.Request.Builder toOpenMrcRequest(String request) {
        OpenMrc.Request.Builder builder = OpenMrc.Request.newBuilder();

        try {
            JsonFormat.merge(request, extensionRegistry, builder);
        } catch (Exception e) {
            throw new OpenMrcMappingException(e);
        }

        try {
            validator.validate(builder);
        } catch (OpenMrcValidator.OpenMrcValidationException e) {
            throw new OpenMrcMappingException(e);
        }

        return builder;
    }

    @Override
    public OpenMrc.Response.Builder toOpenMrcResponse(@Nullable String request, String response) {
        OpenMrc.Response.Builder builder = OpenMrc.Response.newBuilder();

        try {
            JsonFormat.merge(response, extensionRegistry, builder);
        } catch (Exception e) {
            throw new OpenMrcMappingException(e);
        }

        try {
            validator.validate(builder);
        } catch (OpenMrcValidator.OpenMrcValidationException e) {
            throw new OpenMrcMappingException(e);
        }

        return builder;
    }
}
