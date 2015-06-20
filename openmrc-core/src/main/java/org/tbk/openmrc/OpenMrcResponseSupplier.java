package org.tbk.openmrc;

import java.util.function.Function;

/**
 * Created by void on 20.06.15.
 */
public interface OpenMrcResponseSupplier extends Function<OpenMrc.Request, OpenMrc.Response.Builder> {
}
