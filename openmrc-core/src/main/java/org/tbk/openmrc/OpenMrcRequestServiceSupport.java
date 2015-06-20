package org.tbk.openmrc;

import org.tbk.openmrc.mapper.OpenMrcMapper;

/**
 * Created by void on 20.06.15.
 */
public abstract class OpenMrcRequestServiceSupport<Req, Res> implements OpenMrcRequestService<Req, Res> {

    private OpenMrcMapper<Req, Res, Req, Res> mapper;

    public OpenMrcRequestServiceSupport(OpenMrcMapper<Req, Res, Req, Res> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Res apply(Req context) {
        OpenMrc.Request.Builder requestBuilder = mapper.toOpenMrcRequest(context);
        OpenMrc.Request request = requestBuilder.buildPartial();

        OpenMrc.Response.Builder responseBuilder = processRequest(request);

        return mapper.toExchangeResponse(request, responseBuilder.buildPartial());
    }

    protected abstract OpenMrc.Response.Builder processRequest(OpenMrc.Request req);
}
