package com.github.theborakompanioni.openmrc.mother.protobuf;


import com.github.theborakompanioni.openmrc.OpenMrc;

/**
 * Created by void on 19.06.15.
 */
public class InitialRequestProtobufMother {

    public OpenMrc.Request.Builder standardInitialRequest() {
        return OpenMrc.Request.newBuilder()
                .setSessionId("123")
                .setMonitorId("123")
                .setType(OpenMrc.RequestType.INITIAL)
                .setInitial(OpenMrc.InitialContext.newBuilder()
                                .setTimeStarted(10000)
                                .setState(OpenMrc.VisibilityState.newBuilder()
                                                .setCode(0)
                                                .setState(OpenMrc.Visibility.fullyvisible)
                                                .setFullyvisible(true)
                                                .setVisible(true)
                                                .setHidden(false)
                                                .setPercentage(0.99f)
                                )
                );
    }


    public OpenMrc.Request.Builder standardGenericRequest() {
        return standardInitialRequest()
                .setType(OpenMrc.RequestType.GENERIC)
                .setStatus(OpenMrc.StatusContext.newBuilder()
                                .addTest(OpenMrc.PercentageTimeTest.newBuilder()
                                                .setMonitorState(OpenMrc.VisibilityState.newBuilder()
                                                                .setCode(0)
                                                                .setState(OpenMrc.Visibility.fullyvisible)
                                                                .setFullyvisible(true)
                                                                .setVisible(true)
                                                                .setHidden(false)
                                                                .setPercentage(0.99f)
                                                )
                                                .setTestConfig(OpenMrc.VisibilityTimeTestConfig.newBuilder()
                                                                .setInterval(100)
                                                                .setPercentageLimit(0.5f)
                                                                .setTimeLimit(1000)
                                                )
                                                .setTimeReport(OpenMrc.VisibilityTimeReport.newBuilder()
                                                        .setTimeStarted(100202000)
                                                        .setDuration(232000)
                                                        .setTimeFullyVisible(10000)
                                                        .setTimeVisible(10000)
                                                        .setTimeHidden(10000)
                                                        .setTimeRelativeVisible(100)
                                                        .setPercentage(OpenMrc.PercentageReport.newBuilder()
                                                                        .setCurrent(0.99f)
                                                                        .setMinimum(0.23f)
                                                                        .setMaximum(0.99f)
                                                        ))
                                )
                )
                .setSummary(OpenMrc.SummaryContext.newBuilder()
                        .setReport(OpenMrc.VisibilityTimeReport.newBuilder()
                                        .setTimeStarted(100202000)
                                        .setDuration(232000)
                                        .setTimeFullyVisible(10000)
                                        .setTimeVisible(10000)
                                        .setTimeHidden(10000)
                                        .setTimeRelativeVisible(10)
                                        .setPercentage(OpenMrc.PercentageReport.newBuilder()
                                                        .setCurrent(0.99f)
                                                        .setMinimum(0.23f)
                                                        .setMaximum(0.99f)
                                        )
                        ));
    }
}
