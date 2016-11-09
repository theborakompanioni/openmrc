package com.github.theborakompanioni.openmrc;

import com.github.theborakompanioni.openmrc.mapper.OpenMrcMapper;
import com.google.protobuf.UninitializedMessageException;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public abstract class OpenMrcRequestServiceSupport<Req, Res> implements OpenMrcRequestService<Req, Res> {
    private static OpenMrc.Response INVALID_REQUEST = OpenMrc.Response.newBuilder()
            .setId(OpenMrc.Response.ErrorReason.INVALID_REQUEST.name())
            .setError(OpenMrc.Response.ErrorReason.INVALID_REQUEST)
            .build();

    private static OpenMrc.Response UNKNOWN_ERROR = OpenMrc.Response.newBuilder()
            .setId(OpenMrc.Response.ErrorReason.UNKNOWN_ERROR.name())
            .setError(OpenMrc.Response.ErrorReason.UNKNOWN_ERROR)
            .build();

    private final OpenMrcMapper<Req, Res, Req, Res> mapper;

    public OpenMrcRequestServiceSupport(OpenMrcMapper<Req, Res, Req, Res> mapper) {
        this.mapper = requireNonNull(mapper);
    }

    @Override
    public Res apply(Req context) {
        try {
            OpenMrc.Request.Builder requestBuilder = mapper.toOpenMrcRequest(context);
            OpenMrc.Request request = requestBuilder.build();

            OpenMrc.Response.Builder responseBuilder = processRequest(request);

            Res res = mapper.toExchangeResponse(request, responseBuilder.build());

            return res;
        } catch (OpenMrcMapper.OpenMrcMappingException e) {
            return mapper.toExchangeResponse(null, INVALID_REQUEST);
        } catch (UninitializedMessageException e) {
            return mapper.toExchangeResponse(null, INVALID_REQUEST);
        } catch (Exception e) {
            return mapper.toExchangeResponse(null, UNKNOWN_ERROR);
        }
    }

    protected abstract OpenMrc.Response.Builder processRequest(OpenMrc.Request req);
}
