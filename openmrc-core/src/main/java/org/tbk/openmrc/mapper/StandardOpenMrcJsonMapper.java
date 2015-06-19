package org.tbk.openmrc.mapper;

import com.codahale.metrics.MetricRegistry;
import com.google.protobuf.ExtensionRegistry;
import com.googlecode.protobuf.format.JsonFormat;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrc.Request;
import org.tbk.openmrc.OpenMrc.Response;
import org.tbk.openmrc.util.OpenMrcValidator;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class StandardOpenMrcJsonMapper implements OpenMrcMapper<String, String, String, String> {

    private ExtensionRegistry extensionRegistry;
    private OpenMrcValidator validator;

    public StandardOpenMrcJsonMapper() {
        this(ExtensionRegistry.newInstance(), new MetricRegistry());
    }

    public StandardOpenMrcJsonMapper(MetricRegistry metricRegistry) {
        this(ExtensionRegistry.newInstance(), metricRegistry);
    }

    public StandardOpenMrcJsonMapper(ExtensionRegistry extensionRegistry) {
        this(extensionRegistry, new MetricRegistry());
    }

    public StandardOpenMrcJsonMapper(ExtensionRegistry extensionRegistry, MetricRegistry metricRegistry) {
        this.extensionRegistry = extensionRegistry;
        this.validator = new OpenMrcValidator(metricRegistry);
    }

    @Override
    public String toExchangeRequest(@Nullable Request request) {
        try {
            return JsonFormat.printToString(request);
        } catch (Exception e) {
            throw new OpenMrcMappingException(e);
        }
    }

    @Override
    public String toExchangeResponse(@Nullable Request request, Response response) {
        try {
            return JsonFormat.printToString(response);
        } catch (Exception e) {
            throw new OpenMrcMappingException(e);
        }
    }

    @Override
    public Request.Builder toOpenMrcRequest(String request) {
        OpenMrc.Request.Builder builder = OpenMrc.Request.newBuilder();

        try {
            JsonFormat.merge(request, extensionRegistry, builder);
            validator.validate(builder);
        } catch (Exception e) {
            throw new OpenMrcMappingException(e);
        }

        return builder;
    }

    @Override
    public Response.Builder toOpenMrcResponse(@Nullable String request, String response) {
        OpenMrc.Response.Builder builder = OpenMrc.Response.newBuilder();

        try {
            JsonFormat.merge(response, extensionRegistry, builder);
            validator.validate(builder);
        } catch (Exception e) {
            throw new OpenMrcMappingException(e);
        }

        return builder;
    }
}
