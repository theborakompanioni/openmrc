package org.tbk.openmrc.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.mapper.StandardOpenMrcJsonMapper;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by void on 20.06.15.
 */
public class OpenMrcJsonHttpRequestMapper implements OpenMrcHttpRequestMapper {

    private static final Logger log = LoggerFactory.getLogger(OpenMrcJsonHttpRequestMapper.class);

    private final StandardOpenMrcJsonMapper openMrcJsonMapper;

    private final List<OpenMrcRequestInterceptor<HttpServletRequest>> requestInterceptor;
    private final ObjectMapper mapper;

    public OpenMrcJsonHttpRequestMapper(StandardOpenMrcJsonMapper standardOpenMrcJsonMapper, List<OpenMrcRequestInterceptor<HttpServletRequest>> requestInterceptor) {
        this.openMrcJsonMapper = standardOpenMrcJsonMapper;
        this.requestInterceptor = requestInterceptor;
        this.mapper = createObjectMapper();
    }

    @Override
    public HttpServletRequest toExchangeRequest(@Nullable OpenMrc.Request request) throws OpenMrcMappingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public OpenMrc.Response.Builder toOpenMrcResponse(@Nullable HttpServletRequest request, HttpServletResponse response) throws OpenMrcMappingException {
        return null;
    }

    @Override
    public HttpServletResponse toExchangeResponse(@Nullable OpenMrc.Request request, OpenMrc.Response response) throws OpenMrcMappingException {
        return null;
    }

    @Override
    public OpenMrc.Request.Builder toOpenMrcRequest(HttpServletRequest request) throws OpenMrcMappingException {
        return createBuilder(request);
    }

    private OpenMrc.Request.Builder createBuilder(HttpServletRequest context) {
        try {
            String jsonRequestBody = readAndConvertJsonRequestBody(context, String.class);

            OpenMrc.Request.Builder builderFromJson = openMrcJsonMapper.toOpenMrcRequest(jsonRequestBody);

            return requestInterceptor.stream()
                    .reduce(builderFromJson,
                            (b, interceptor) -> interceptor.intercept(context, b),
                            (b1, b2) -> b1.mergeFrom(b2.buildPartial()));

        } catch (IOException e) {
            log.error("IOExeption while parsing HttpServletRequest to OpenMrc.Request", e);
            throw Throwables.propagate(e);
        }
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }

    private <T> T readAndConvertJsonRequestBody(HttpServletRequest request, Class<T> clazz) throws IOException {
        JsonNode jsonNode = readRequestBodyAsJson(request);
        return mapper.convertValue(jsonNode, clazz);
    }

    private JsonNode readRequestBodyAsJson(HttpServletRequest request) throws IOException {
        return mapper.readTree(request.getReader());
    }
}
