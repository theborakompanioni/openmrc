package com.github.theborakompanioni.openmrc;

import io.reactivex.Observable;

import java.util.function.Function;

public interface OpenMrcResponseSupplier extends Function<OpenMrc.Request,
        Observable<OpenMrc.Response.Builder>> {
}
