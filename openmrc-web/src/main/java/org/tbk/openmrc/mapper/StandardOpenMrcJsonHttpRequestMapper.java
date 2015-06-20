package org.tbk.openmrc.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletResponse;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcRequestInterceptor;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Created by void on 20.06.15.
 */
public class StandardOpenMrcJsonHttpRequestMapper implements OpenMrcHttpRequestMapper {

    private static final Logger log = LoggerFactory.getLogger(StandardOpenMrcJsonHttpRequestMapper.class);

    private final StandardOpenMrcJsonMapper openMrcJsonMapper;

    private final List<OpenMrcRequestInterceptor<HttpServletRequest>> requestInterceptor;

    private final ObjectMapper mapper;

    private final Function<OpenMrc.Response, Integer> getStatusCode = (response) -> {
        if (!response.hasError()) {
            return 202; // accepted
        }

        switch (response.getError()) {
            case INVALID_REQUEST:
                return 400;
            case BLOCKED_PUBLISHER:
                return 403; // forbidden
            default:
                return 500;
        }
    };

    public StandardOpenMrcJsonHttpRequestMapper(StandardOpenMrcJsonMapper standardOpenMrcJsonMapper) {
        this(standardOpenMrcJsonMapper, Collections.emptyList());
    }

    public StandardOpenMrcJsonHttpRequestMapper(StandardOpenMrcJsonMapper standardOpenMrcJsonMapper, List<OpenMrcRequestInterceptor<HttpServletRequest>> requestInterceptor) {
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
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpServletResponse toExchangeResponse(@Nullable OpenMrc.Request request, OpenMrc.Response response) throws OpenMrcMappingException {
        MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
        mockHttpServletResponse.setStatus(getStatusCode.apply(response));

        try {
            mockHttpServletResponse.getWriter().write(openMrcJsonMapper.toExchangeResponse(request, response));

            return mockHttpServletResponse;
        } catch (UnsupportedEncodingException e) {
            throw new OpenMrcMappingException(e);
        }
    }

    @Override
    public OpenMrc.Request.Builder toOpenMrcRequest(HttpServletRequest request) throws OpenMrcMappingException {
        return createBuilder(request);
    }

    private OpenMrc.Request.Builder createBuilder(HttpServletRequest context) {
        try {
            String jsonRequestBody = readRequestBodyAsJson(context).toString();

            OpenMrc.Request.Builder builderFromJson = openMrcJsonMapper.toOpenMrcRequest(jsonRequestBody);

            return requestInterceptor.stream()
                    .reduce(builderFromJson,
                            (b, interceptor) -> interceptor.intercept(context, b),
                            (b1, b2) -> b1.mergeFrom(b2.buildPartial()));

        } catch (IOException e) {
            log.error("IOExeption while parsing HttpServletRequest as OpenMrc.Request", e);
            throw new OpenMrcMappingException(e);
        }
    }

    private ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }

    private JsonNode readRequestBodyAsJson(HttpServletRequest request) throws IOException {
        return mapper.readTree(request.getReader());
    }
}
