package com.github.theborakompanioni.openmrc.spring.mapper;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.json.StandardOpenMrcJsonMapper;
import io.reactivex.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class StandardOpenMrcJsonHttpRequestMapper implements OpenMrcHttpRequestMapper {

    private static final Logger log = LoggerFactory.getLogger(StandardOpenMrcJsonHttpRequestMapper.class);

    private final StandardOpenMrcJsonMapper openMrcJsonMapper;

    // TODO: try to get rid of dependency
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
        this.openMrcJsonMapper = requireNonNull(standardOpenMrcJsonMapper);
        this.mapper = createObjectMapper();
    }

    @Override
    public Observable<HttpServletRequest> toExchangeRequest(@Nullable OpenMrc.Request request) throws OpenMrcMappingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<OpenMrc.Response.Builder> toOpenMrcResponse(@Nullable HttpServletRequest request, ResponseEntity<String> response) throws OpenMrcMappingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<ResponseEntity<String>> toExchangeResponse(HttpServletRequest originalRequest, @Nullable OpenMrc.Request request, OpenMrc.Response response) throws OpenMrcMappingException {
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.status(getStatusCode.apply(response));
        return openMrcJsonMapper.toExchangeResponse(null, request, response)
                .map(responseEntity::body);
    }

    @Override
    public Observable<OpenMrc.Request.Builder> toOpenMrcRequest(HttpServletRequest request) throws OpenMrcMappingException {
        return createBuilder(request);
    }

    private Observable<OpenMrc.Request.Builder> createBuilder(HttpServletRequest context) {
        Observable<String> jsonRequestBody = Observable.defer(() -> {
            try {
                return Observable.just(readRequestBodyAsJson(context).toString());
            } catch (IOException e) {
                log.error("IOExeption while parsing HttpServletRequest as OpenMrc.Request", e);
                throw new OpenMrcMappingException(e);
            }
        });

        Observable<OpenMrc.Request.Builder> builderFromJson = jsonRequestBody.flatMap(openMrcJsonMapper::toOpenMrcRequest);

        return builderFromJson;
    }

    private ObjectMapper createObjectMapper() {
        return new ObjectMapper();
    }

    private JsonNode readRequestBodyAsJson(HttpServletRequest request) throws IOException {
        return mapper.readTree(request.getReader());
    }
}
