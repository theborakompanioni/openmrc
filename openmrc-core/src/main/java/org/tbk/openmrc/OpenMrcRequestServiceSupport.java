package org.tbk.openmrc;

import org.tbk.openmrc.mapper.OpenMrcMapper;

/**
 * Created by void on 20.06.15.
 */
public abstract class OpenMrcRequestServiceSupport<Req, Res> implements OpenMrcRequestService<Req, Res> {
    private static OpenMrc.Response INVALID_REQUEST = OpenMrc.Response.newBuilder()
            .setId(OpenMrc.Response.ErrorReason.INVALID_REQUEST.name())
            .setError(OpenMrc.Response.ErrorReason.INVALID_REQUEST)
            .build();
    private static OpenMrc.Response UNKNOWN_ERROR = OpenMrc.Response.newBuilder()
            .setId(OpenMrc.Response.ErrorReason.UNKNOWN_ERROR.name())
            .setError(OpenMrc.Response.ErrorReason.UNKNOWN_ERROR)
            .build();

    private OpenMrcMapper<Req, Res, Req, Res> mapper;

    public OpenMrcRequestServiceSupport(OpenMrcMapper<Req, Res, Req, Res> mapper) {
        this.mapper = mapper;
    }

    @Override
    public Res apply(Req context) {
        try {
            OpenMrc.Request.Builder requestBuilder = mapper.toOpenMrcRequest(context);
            OpenMrc.Request request = requestBuilder.build();

            OpenMrc.Response.Builder responseBuilder = processRequest(request);

            return mapper.toExchangeResponse(request, responseBuilder.build());
        } catch (OpenMrcMapper.OpenMrcMappingException e) {
            return mapper.toExchangeResponse(null, INVALID_REQUEST);
        } catch (Exception e) {
            return mapper.toExchangeResponse(null, UNKNOWN_ERROR);
        }
    }

    protected abstract OpenMrc.Response.Builder processRequest(OpenMrc.Request req);
}
