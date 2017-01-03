package com.github.theborakompanioni.openmrc;

import com.github.theborakompanioni.openmrc.spring.mapper.OpenMrcMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.UninitializedMessageException;
import io.reactivex.Observable;

import java.util.List;
import java.util.Map;

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
    private final List<OpenMrcRequestConsumer> requestConsumer;

    public OpenMrcRequestServiceSupport(OpenMrcMapper<Req, Res, Req, Res> mapper) {
        this(mapper, ImmutableList.of());
    }

    public OpenMrcRequestServiceSupport(OpenMrcMapper<Req, Res, Req, Res> mapper, List<OpenMrcRequestConsumer> requestConsumer) {
        this.mapper = requireNonNull(mapper);
        this.requestConsumer = requestConsumer;
    }

    @Override
    public Observable<Res> apply(Req context) {
        Observable<OpenMrc.Request.Builder> requestBuilder = mapper.toOpenMrcRequest(context);
        Observable<OpenMrc.Request> request = requestBuilder.map(OpenMrc.Request.Builder::build);

        Observable<Map<OpenMrc.Request, OpenMrc.Response.Builder>> responseBuilder = request.flatMap(req -> {
            final Observable<OpenMrc.Request> consumedRequest = Observable.fromIterable(requestConsumer)
                    .map(consumer -> {
                        consumer.accept(req);
                        return req;
                    })
                    .lastOrError()
                    .toObservable();

            final Observable<OpenMrc.Response.Builder> processedRequest = consumedRequest.flatMap(this::processRequest);
            return Observable.zip(Observable.just(req), processedRequest, ImmutableMap::of);
        });

        final Observable<Res> resObservable = responseBuilder.flatMap(m -> Observable.fromIterable(m.entrySet()))
                .firstElement()
                .toObservable()
                .flatMap(pair -> mapper.toExchangeResponse(pair.getKey(), pair.getValue().build()));

        return resObservable.onErrorResumeNext(throwable -> {
            if (throwable instanceof OpenMrcMapper.OpenMrcMappingException) {
                return mapper.toExchangeResponse(null, INVALID_REQUEST);
            } else if (throwable instanceof UninitializedMessageException) {
                return mapper.toExchangeResponse(null, INVALID_REQUEST);
            }
            return mapper.toExchangeResponse(null, UNKNOWN_ERROR);

        });
    }

    protected abstract Observable<OpenMrc.Response.Builder> processRequest(OpenMrc.Request req);
}
