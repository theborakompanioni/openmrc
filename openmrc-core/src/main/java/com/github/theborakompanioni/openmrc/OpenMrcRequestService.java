package com.github.theborakompanioni.openmrc;

import io.reactivex.Observable;

import java.util.function.Function;

public interface OpenMrcRequestService<Req, Res> extends Function<Req, Observable<Res>> {

}
