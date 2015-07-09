package com.github.theborakompanioni.openmrc.web;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.github.theborakompanioni.openmrc.OpenMrcRequestServiceSupport;
import com.github.theborakompanioni.openmrc.mapper.OpenMrcHttpRequestMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by void on 20.06.15.
 */
public class OpenMrcHttpRequestService extends OpenMrcRequestServiceSupport<HttpServletRequest, HttpServletResponse> {

    private final List<OpenMrcRequestConsumer> requestConsumer;

    public OpenMrcHttpRequestService(OpenMrcHttpRequestMapper mapper, List<OpenMrcRequestConsumer> requestConsumer) {
        super(mapper);
        this.requestConsumer = Objects.requireNonNull(requestConsumer);
    }

    @Override
    public OpenMrc.Response.Builder processRequest(OpenMrc.Request request) {
        requestConsumer.forEach(consumer -> consumer.accept(request));

        return OpenMrc.Response.newBuilder()
                .setId(UUID.randomUUID().toString());
    }
}
