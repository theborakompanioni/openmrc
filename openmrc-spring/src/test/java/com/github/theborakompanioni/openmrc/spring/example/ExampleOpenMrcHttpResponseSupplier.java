package com.github.theborakompanioni.openmrc.spring.example;

import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcResponseSupplier;
import io.reactivex.Observable;

import java.util.UUID;

public class ExampleOpenMrcHttpResponseSupplier implements OpenMrcResponseSupplier {

    @Override
    public Observable<OpenMrc.Response.Builder> apply(OpenMrc.Request request) {
        final OpenMrc.Response.Builder builder = OpenMrc.Response.newBuilder()
                .setId(UUID.randomUUID().toString());
        return Observable.just(builder);
    }
}
