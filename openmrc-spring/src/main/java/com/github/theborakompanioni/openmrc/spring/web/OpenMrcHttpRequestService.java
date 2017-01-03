package com.github.theborakompanioni.openmrc.spring.web;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.github.theborakompanioni.openmrc.OpenMrcRequestServiceSupport;
import com.github.theborakompanioni.openmrc.spring.mapper.OpenMrcHttpRequestMapper;
import io.reactivex.Observable;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

public class OpenMrcHttpRequestService extends OpenMrcRequestServiceSupport<HttpServletRequest, ResponseEntity<String>> {

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
