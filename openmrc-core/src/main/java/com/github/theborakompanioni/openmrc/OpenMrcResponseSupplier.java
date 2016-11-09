package com.github.theborakompanioni.openmrc;

import java.util.function.Function;

public interface OpenMrcResponseSupplier extends Function<OpenMrc.Request, OpenMrc.Response.Builder> {
}
