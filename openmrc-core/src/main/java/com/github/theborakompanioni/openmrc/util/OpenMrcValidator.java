package com.github.theborakompanioni.openmrc.util;

import com.github.theborakompanioni.openmrc.OpenMrc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpenMrcValidator {

    public static class OpenMrcValidationException extends Exception {
        public OpenMrcValidationException(String message) {
            super(message);
        }

        public OpenMrcValidationException(String message, Throwable t) {
            super(message, t);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(OpenMrcValidator.class);

    public OpenMrcValidator() {
    }

    public void validate(OpenMrc.Response.Builder builder) throws OpenMrcValidationException {

    }

    public void validate(OpenMrc.Request.Builder request) throws OpenMrcValidationException {
        try {
            validateInternal(request);
        } catch (OpenMrcValidationException e) {
            throw e;
        }
    }

    protected void validateInternal(OpenMrc.Request.Builder request) throws OpenMrcValidationException {
        if (request.getType() == OpenMrc.RequestType.INITIAL || request.hasInitial()) {
            validateInitialContext(request);
        }

        if (request.getType() == OpenMrc.RequestType.STATUS || request.hasStatus()) {
            validateStatusContext(request);
        }

        if (request.getType() == OpenMrc.RequestType.SUMMARY || request.hasSummary()) {
            validateSummaryContext(request);
        }
    }

    protected void validateInitialContext(OpenMrc.Request.Builder request) throws OpenMrcValidationException {
        if (!request.hasInitial()) {
            if (logger.isDebugEnabled()) {
                logger.debug("{} rejected, Missing field 'initial'", request.getSessionId());
            }
            throw new OpenMrcValidationException("Missing field 'initial'");
        }

        OpenMrc.InitialContext context = request.getInitial();
        if (!context.hasState()) {
            if (logger.isDebugEnabled()) {
                logger.debug("{} rejected, Missing field 'state'", request.getSessionId());
            }
            throw new OpenMrcValidationException("Missing field 'state'");
        }

        validateVisibilityState(request, request.getInitial().getState());
    }

    protected void validateVisibilityState(OpenMrc.Request.Builder request, OpenMrc.VisibilityState state) throws OpenMrcValidationException {
        boolean hasLeastOneFlag = state.getFullyvisible() || state.getVisible() || state.getHidden();
        boolean hiddenContradiction = state.getHidden() && (state.getFullyvisible() || state.getVisible());
        boolean visibleContradiction = state.getFullyvisible() && !state.getVisible();

        boolean valid = hasLeastOneFlag && !hiddenContradiction && !visibleContradiction;

        if (!valid) {
            if (logger.isDebugEnabled()) {
                logger.debug("{} rejected, Invalid field 'state'", request.getSessionId());
            }
            throw new OpenMrcValidationException("Invalid field 'state'");
        }

    }

    protected void validateStatusContext(OpenMrc.Request.Builder request) throws OpenMrcValidationException {
        if (!request.hasStatus()) {
            if (logger.isDebugEnabled()) {
                logger.debug("{} rejected, Missing field 'status'", request.getSessionId());
            }
            throw new OpenMrcValidationException("Missing field 'status'");
        }
    }

    protected void validateSummaryContext(OpenMrc.Request.Builder request) throws OpenMrcValidationException {
        if (!request.hasSummary()) {
            if (logger.isDebugEnabled()) {
                logger.debug("{} rejected, Missing field 'summary'", request.getSessionId());
            }
            throw new OpenMrcValidationException("Missing field 'summary'");
        }
    }
}
