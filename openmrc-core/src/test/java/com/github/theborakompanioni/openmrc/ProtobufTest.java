package com.github.theborakompanioni.openmrc;

import com.github.theborakompanioni.openmrc.mother.InitialRequests;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class ProtobufTest {

    private List<OpenMrc.Request.Builder> validRequestProtbufBuilderParams() {
        return Arrays.asList(
                InitialRequests.protobuf().standardInitialRequest(),
                InitialRequests.protobuf().standardInitialRequest()
                        .setExtension(OpenMrcExtensions.Browser.browser, OpenMrcExtensions.Browser.newBuilder()
                                .setName("FIREFOX")
                                .setType("Gecko")
                                .setVersion("36")
                                .setMajorVersion("36")
                                .setManufacturer("Mozilla")
                                .build())
                        .setExtension(OpenMrcExtensions.OperatingSystem.operatingSystem, OpenMrcExtensions.OperatingSystem.newBuilder()
                                .setName("Windows 8.1")
                                .setGroupName("WINDOWS")
                                .setManufacturer("Microsoft")
                                .build()),
                InitialRequests.protobuf().standardGenericRequest()
        );
    }

    @Test
    @Parameters(method = "validRequestProtbufBuilderParams")
    public void itShouldBuildRequestObjects(OpenMrc.Request.Builder builder) {
        assertThat(builder.build(), is(not(nullValue())));
    }

    @Test
    public void testExtendedRequest() {
        OpenMrc.Request initialRequest = InitialRequests.protobuf().standardInitialRequest()
                .setExtension(OpenMrcExtensions.Browser.browser, OpenMrcExtensions.Browser.newBuilder()
                        .setName("FIREFOX")
                        .setType("Gecko")
                        .setVersion("36")
                        .setMajorVersion("36")
                        .setManufacturer("Mozilla")
                        .build())
                .setExtension(OpenMrcExtensions.OperatingSystem.operatingSystem, OpenMrcExtensions.OperatingSystem.newBuilder()
                        .setName("Windows 8.1")
                        .setGroupName("WINDOWS")
                        .setManufacturer("Microsoft")
                        .build())
                .build();

        OpenMrcExtensions.Browser browser = initialRequest.getExtension(OpenMrcExtensions.Browser.browser);
        assertThat(browser, is(not(nullValue())));
        assertThat(browser.getName(), equalTo("FIREFOX"));

        OpenMrcExtensions.OperatingSystem operatingSystem = initialRequest.getExtension(OpenMrcExtensions.OperatingSystem.operatingSystem);

        assertThat(operatingSystem, is(not(nullValue())));
        assertThat(operatingSystem.getName(), equalTo("Windows 8.1"));
    }

}
