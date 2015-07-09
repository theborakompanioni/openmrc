package com.github.theborakompanioni.openmrc.util;

import com.codahale.metrics.MetricRegistry;
import com.github.theborakompanioni.openmrc.OpenMrc;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

/**
 * Created by void on 14.06.15.
 */
public class OpenMrcValidatorTest {

    private OpenMrcValidator validator;

    @Before
    public void setup() {
        MetricRegistry metricRegistry = mock(MetricRegistry.class);
        validator = new OpenMrcValidator(metricRegistry);
    }

    @Test(expected = OpenMrcValidator.OpenMrcValidationException.class)
    public void testThatInitialRequestHasInitialContext() throws OpenMrcValidator.OpenMrcValidationException {
        OpenMrc.Request initialRequest = OpenMrc.Request.newBuilder()
                .setSessionId("123")
                .setMonitorId("123")
                .setType(OpenMrc.RequestType.INITIAL)
                .build();

        validator.validate(initialRequest.toBuilder());
    }

    @Test(expected = OpenMrcValidator.OpenMrcValidationException.class)
    public void testThatInitialRequestHasValidInitialContextFail1() throws OpenMrcValidator.OpenMrcValidationException {
        OpenMrc.Request initialRequest = OpenMrc.Request.newBuilder()
                .setSessionId("123")
                .setMonitorId("123")
                .setType(OpenMrc.RequestType.INITIAL)
                .setInitial(OpenMrc.InitialContext.newBuilder()
                                .setTimeStarted(10000)
                                .setState(OpenMrc.VisibilityState.newBuilder()
                                                .setCode(0)
                                                .setState(OpenMrc.Visibility.hidden)
                                                .setFullyvisible(true)
                                                .setVisible(false)
                                                .setHidden(true)
                                                .setPercentage(1.0f)
                                )
                ).build();


        validator.validate(initialRequest.toBuilder());
    }


    @Test
    public void testThatInitialRequestHasValidInitialContextSuccess() throws OpenMrcValidator.OpenMrcValidationException {
        OpenMrc.Request initialRequest = OpenMrc.Request.newBuilder()
                .setSessionId("123")
                .setMonitorId("123")
                .setType(OpenMrc.RequestType.INITIAL)
                .setInitial(OpenMrc.InitialContext.newBuilder()
                                .setTimeStarted(1434320037000L)
                                .setState(OpenMrc.VisibilityState.newBuilder()
                                                .setCode(2)
                                                .setState(OpenMrc.Visibility.fullyvisible)
                                                .setFullyvisible(true)
                                                .setVisible(true)
                                                .setHidden(false)
                                                .setPercentage(1.0f)
                                )
                ).build();

        validator.validate(initialRequest.toBuilder());
    }

}