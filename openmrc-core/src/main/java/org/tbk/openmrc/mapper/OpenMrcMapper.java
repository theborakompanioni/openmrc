package org.tbk.openmrc.mapper;

import org.tbk.openmrc.OpenMrc.Request;
import org.tbk.openmrc.OpenMrc.Response;

import javax.annotation.Nullable;

/**
 * Converts between OpenMrc and exchange-specific requests/response.
 * <p>
 * Implementations of this interface have to be threadsafe.
 *
 * @param <ReqIn>   Type for the exchange-specific request model (input)
 * @param <RespIn>  Type for the exchange-specific response model (input)
 * @param <ReqOut>  Type for the exchange-specific request model (output)
 * @param <RespOut> Type for the exchange-specific response model (output)
 */
public interface OpenMrcMapper<ReqIn, RespIn, ReqOut, RespOut> {

    class OpenMrcMappingException extends RuntimeException {
        public OpenMrcMappingException(Throwable t) {
            super(t);
        }
    }
    /**
     * Converts an OpenMrc request to the exchange-specific format.
     *
     * @param request OpenMrc request
     * @return Request in the exchange-specific format
     */
    ReqOut toExchangeRequest(@Nullable Request request) throws OpenMrcMappingException;

    /**
     * Converts an OpenMrc response to the exchange-specific format.
     *
     * @param request  OpenMrc request, if necessary for context or validations
     * @param response OpenMrc response
     * @return Response in the exchange-specific format
     */
    RespOut toExchangeResponse(@Nullable Request request, Response response) throws OpenMrcMappingException;

    /**
     * Converts an exchange-specific request to OpenMrc.
     *
     * @param request Request in the exchange-specific format
     * @return OpenMrc request
     */
    Request.Builder toOpenMrcRequest(ReqIn request) throws OpenMrcMappingException;

    /**
     * Converts a n exchange-specific response to OpenMrc.
     *
     * @param request  Request in the exchange-specific format, if necessary for context or validations
     * @param response The response
     * @return OpenMrc response
     */
    Response.Builder toOpenMrcResponse(@Nullable ReqIn request, RespIn response) throws OpenMrcMappingException;

}