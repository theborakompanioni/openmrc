package com.github.theborakompanioni.openmrc;

import com.github.theborakompanioni.openmrc.mapper.OpenMrcMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.protobuf.UninitializedMessageException;
import io.reactivex.Observable;

import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class OpenMrcRequestServiceImpl<Req, Res> implements OpenMrcRequestService<Req, Res> {
    private static OpenMrc.Response INVALID_REQUEST = OpenMrc.Response.newBuilder()
            .setId(OpenMrc.Response.ErrorReason.INVALID_REQUEST.name())
            .setError(OpenMrc.Response.ErrorReason.INVALID_REQUEST)
            .build();

    private static OpenMrc.Response UNKNOWN_ERROR = OpenMrc.Response.newBuilder()
            .setId(OpenMrc.Response.ErrorReason.UNKNOWN_ERROR.name())
            .setError(OpenMrc.Response.ErrorReason.UNKNOWN_ERROR)
            .build();

    private final OpenMrcMapper<Req, Res, Req, Res> mapper;
    private final OpenMrcResponseSupplier responseSupplier;
    private final List<OpenMrcRequestInterceptor<Req>> requestInterceptor;
    private final List<OpenMrcRequestConsumer> requestConsumer;

    public OpenMrcRequestServiceImpl(OpenMrcMapper<Req, Res, Req, Res> mapper, OpenMrcResponseSupplier responseSupplier) {
        this(mapper, responseSupplier, ImmutableList.of(), ImmutableList.of());
    }

    public OpenMrcRequestServiceImpl(OpenMrcMapper<Req, Res, Req, Res> mapper,
                                     OpenMrcResponseSupplier responseSupplier,
                                     List<OpenMrcRequestInterceptor<Req>> requestInterceptor,
                                     List<OpenMrcRequestConsumer> requestConsumer) {
        this.mapper = requireNonNull(mapper);
        this.responseSupplier = requireNonNull(responseSupplier);
        this.requestInterceptor = requireNonNull(requestInterceptor);
        this.requestConsumer = requireNonNull(requestConsumer);
    }

    @Override
    public Observable<Res> apply(Req context) {
        Observable<OpenMrc.Request.Builder> requestBuilder = mapper.toOpenMrcRequest(context);

        Observable<OpenMrc.Request.Builder> interceptedRequestBuilder = requestBuilder
                .flatMap(builder -> Observable.fromIterable(requestInterceptor)
                        .flatMap(interceptor -> interceptor.intercept(context, builder))
                        .reduce((b1, b2) -> b1.mergeFrom(b2.buildPartial()))
                        .toObservable());

        Observable<OpenMrc.Request> request = interceptedRequestBuilder
                .map(OpenMrc.Request.Builder::build);

        Observable<Map<OpenMrc.Request, OpenMrc.Response.Builder>> responseBuilder = request
                .flatMap(req -> {
                    final Observable<OpenMrc.Request> consumedRequest = Observable.fromIterable(requestConsumer)
                            .doOnNext(consumer -> consumer.accept(req))
                            .lastOrError()
                            .map(consumer -> req)
                            .toObservable();

                    final Observable<OpenMrc.Response.Builder> processedRequest = consumedRequest
                            .flatMap(responseSupplier::apply);
                    return Observable.zip(Observable.just(req), processedRequest, ImmutableMap::of);
                });

        final Observable<Res> resObservable = responseBuilder.flatMap(m -> Observable.fromIterable(m.entrySet()))
                .firstElement()
                .toObservable()
                .flatMap(pair -> mapper.toExchangeResponse(context, pair.getKey(), pair.getValue().build()));

        return resObservable.onErrorResumeNext(throwable -> {
            if (throwable instanceof OpenMrcMapper.OpenMrcMappingException) {
                return mapper.toExchangeResponse(context, null, INVALID_REQUEST);
            } else if (throwable instanceof UninitializedMessageException) {
                return mapper.toExchangeResponse(context, null, INVALID_REQUEST);
            }
            return mapper.toExchangeResponse(context, null, UNKNOWN_ERROR);

        });
    }
}
