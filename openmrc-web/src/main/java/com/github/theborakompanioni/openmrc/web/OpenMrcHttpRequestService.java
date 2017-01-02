package com.github.theborakompanioni.openmrc.web;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.github.theborakompanioni.openmrc.OpenMrcRequestServiceSupport;
import com.github.theborakompanioni.openmrc.mapper.OpenMrcHttpRequestMapper;
import io.reactivex.Observable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public class OpenMrcHttpRequestService extends OpenMrcRequestServiceSupport<HttpServletRequest, HttpServletResponse> {

    public OpenMrcHttpRequestService(OpenMrcHttpRequestMapper mapper, List<OpenMrcRequestConsumer> requestConsumer) {
        super(mapper, requestConsumer);
    }

    @Override
    public Observable<OpenMrc.Response.Builder> processRequest(OpenMrc.Request request) {
        final OpenMrc.Response.Builder builder = OpenMrc.Response.newBuilder()
                .setId(UUID.randomUUID().toString());
        return Observable.just(builder);
    }
}
