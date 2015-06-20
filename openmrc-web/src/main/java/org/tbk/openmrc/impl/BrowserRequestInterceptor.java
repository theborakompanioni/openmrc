package org.tbk.openmrc.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcRequestInterceptor;
import org.tbk.openmrc.mapper.StandardOpenMrcJsonMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by void on 20.06.15.
 */
public class BrowserRequestInterceptor implements OpenMrcRequestInterceptor<HttpServletRequest> {

    private static final Logger log = LoggerFactory.getLogger(BrowserRequestInterceptor.class);

    private final StandardOpenMrcJsonMapper openMrcJsonMapper;
    private final ObjectMapper mapper;

    public BrowserRequestInterceptor(StandardOpenMrcJsonMapper openMrcJsonMapper) {
        this.openMrcJsonMapper = openMrcJsonMapper;
        this.mapper = createObjectMapper();
    }

    @Override
    public OpenMrc.Request.Builder intercept(HttpServletRequest context, OpenMrc.Request.Builder builder) {
        try {
            String jsonRequestBody = readAndConvertJsonRequestBody(context, String.class);

            OpenMrc.Request.Builder builderFromJson = openMrcJsonMapper.toOpenMrcRequest(jsonRequestBody);

            OpenMrc.Request.Builder mergedBuilder = builder.mergeFrom(builderFromJson.buildPartial());

            return mergedBuilder;
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
