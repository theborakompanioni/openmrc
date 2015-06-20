package org.tbk.openmrc.web;

import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcRequestConsumer;
import org.tbk.openmrc.OpenMrcRequestServiceSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by void on 20.06.15.
 */
public class OpenMrcHttpRequestService extends OpenMrcRequestServiceSupport<HttpServletRequest, HttpServletResponse> {

    public OpenMrcHttpRequestService(OpenMrcHttpRequestMapper mapper, List<OpenMrcRequestConsumer> requestConsumer) {
        super(mapper, requestConsumer);
    }

    @Override
    public OpenMrc.Response.Builder processRequest(OpenMrc.Request req) {
        return OpenMrc.Response.newBuilder()
                .setId("1");
    }
}
