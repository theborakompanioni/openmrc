package com.github.theborakompanioni.openmrc.spring.web;

import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.github.theborakompanioni.openmrc.OpenMrcRequestInterceptor;
import com.github.theborakompanioni.openmrc.OpenMrcRequestServiceImpl;
import com.github.theborakompanioni.openmrc.OpenMrcResponseSupplier;
import com.github.theborakompanioni.openmrc.spring.mapper.OpenMrcHttpRequestMapper;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class OpenMrcHttpRequestService extends OpenMrcRequestServiceImpl<HttpServletRequest, ResponseEntity<String>> {

    public OpenMrcHttpRequestService(OpenMrcHttpRequestMapper mapper,
                                     OpenMrcResponseSupplier responseSupplier,
                                     List<OpenMrcRequestInterceptor<HttpServletRequest>> requestInterceptor,
                                     List<OpenMrcRequestConsumer> requestConsumer) {
        super(mapper, responseSupplier, requestInterceptor, requestConsumer);
    }
}
