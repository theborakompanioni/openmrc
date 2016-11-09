package com.github.theborakompanioni.openmrc;

import java.util.function.Function;

public interface OpenMrcRequestService<Req, Res> extends Function<Req, Res> {
}
