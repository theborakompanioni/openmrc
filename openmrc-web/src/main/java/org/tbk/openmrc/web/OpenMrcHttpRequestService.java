package org.tbk.openmrc.web;

import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcRequestServiceSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by void on 20.06.15.
 */
public class OpenMrcHttpRequestService extends OpenMrcRequestServiceSupport<HttpServletRequest, HttpServletResponse> {

    private final List<Consumer<OpenMrc.Request>> requestConsumer;

    public OpenMrcHttpRequestService(OpenMrcHttpRequestMapper mapper, List<Consumer<OpenMrc.Request>> requestConsumer) {
        super(mapper);
        this.requestConsumer = requestConsumer;
    }

    @Override
    public OpenMrc.Response.Builder processRequest(OpenMrc.Request req) {
        requestConsumer.forEach(unit -> unit.accept(req));

        return OpenMrc.Response.newBuilder()
                .setId("1");
    }
}
