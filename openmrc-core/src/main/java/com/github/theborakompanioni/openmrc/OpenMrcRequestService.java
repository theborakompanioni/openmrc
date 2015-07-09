package com.github.theborakompanioni.openmrc;

import java.util.function.Function;

/**
 * Created by void on 20.06.15.
 */
public interface OpenMrcRequestService<Req, Res> extends Function<Req, Res> {
}
