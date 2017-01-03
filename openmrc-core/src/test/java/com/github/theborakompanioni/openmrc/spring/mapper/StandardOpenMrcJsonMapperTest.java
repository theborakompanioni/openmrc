package com.github.theborakompanioni.openmrc.spring.mapper;


import com.codahale.metrics.MetricRegistry;
import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.OpenMrcExtensions;
import com.github.theborakompanioni.openmrc.mapper.OpenMrcMapper;
import com.github.theborakompanioni.openmrc.mapper.StandardOpenMrcJsonMapper;
import com.github.theborakompanioni.openmrc.mother.InitialRequests;
import com.github.theborakompanioni.openmrc.mother.StatusRequests;
import com.google.protobuf.ExtensionRegistry;
import com.googlecode.protobuf.format.JsonFormat;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(JUnitParamsRunner.class)
public class StandardOpenMrcJsonMapperTest {

    private StandardOpenMrcJsonMapper mapper;

    @Before
    public void setup() {
        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(OpenMrcExtensions.Browser.browser);
        registry.add(OpenMrcExtensions.OperatingSystem.operatingSystem);

        mapper = new StandardOpenMrcJsonMapper(registry, mockMetricsRegistry());
    }

    private MetricRegistry mockMetricsRegistry() {
        return new MetricRegistry();
    }

    @Test
    public void testParseInitialRequestWithExtensions() throws JsonFormat.ParseException {
        String jsonFormat = InitialRequests.json().initialRequestWithBrowserExtension();

        OpenMrc.Request parsedRequest = mapper.toOpenMrcRequest(jsonFormat)
                .blockingSingle()
                .build();
        assertThat(parsedRequest.getInitial().getState().getCode(), is(2));
        assertThat(parsedRequest.getInitial().getState().getState(), is(equalTo(OpenMrc.Visibility.fullyvisible)));
        assertThat(parsedRequest.getInitial().getState().getPercentage(), is(0.99f));
        assertThat(parsedRequest.getInitial().getState().getFullyvisible(), is(true));
        assertThat(parsedRequest.getInitial().getState().getVisible(), is(true));
        assertThat(parsedRequest.getInitial().getState().getHidden(), is(false));
    }

    @Test
    @Parameters(method = "validRequestJsonParams")
    public void itShouldBeAbleToParseValidRequestJson(String requestJson) throws JsonFormat.ParseException {
        OpenMrc.Request parsedRequest = mapper.toOpenMrcRequest(requestJson)
                .blockingSingle()
                .build();
        String parsedRequestJson = mapper.toExchangeRequest(parsedRequest).blockingSingle();
        OpenMrc.Request parsedSerializedRequest = mapper.toOpenMrcRequest(parsedRequestJson)
                .blockingSingle()
                .build();

        assertThat(parsedSerializedRequest, is(equalTo(parsedRequest)));
    }

    private List<String> validRequestJsonParams() {
        return Arrays.asList(
                InitialRequests.json().standardInitialRequest(),
                StatusRequests.json().standardStatusRequest(),
                InitialRequests.json().initialRequestWithBrowserExtension()
        );
    }

    @Test(expected = OpenMrcMapper.OpenMrcMappingException.class)
    @Parameters(method = "invalidRequestJsonParams")
    public void itShouldThrowExceptionWhenParsingInvalidRequestJson(String invalidRequestJson) {
        OpenMrc.Request parsedRequest = mapper.toOpenMrcRequest(invalidRequestJson)
                .blockingSingle()
                .build();
        fail("Should have thrown exception when parsing invalid Request in json format");
    }

    private List<String> invalidRequestJsonParams() {
        return Arrays.asList(
                null,
                "",
                "   ",
                "{ invalid json }",
                "{ type:\"INITIAL\" }"
        );
    }

    @Test
    @Parameters(method = "requestProtobufParams")
    public void itShouldBeAbleToSerializeRequestToJson(OpenMrc.Request request) throws JsonFormat.ParseException {
        String requestAsJsonString = mapper.toExchangeRequest(request).blockingSingle();

        OpenMrc.Request parsedSerializedRequest = mapper.toOpenMrcRequest(requestAsJsonString)
                .blockingSingle()
                .build();

        assertThat(parsedSerializedRequest, is(equalTo(request)));
    }

    private List<OpenMrc.Request> requestProtobufParams() {
        return Arrays.asList(
                InitialRequests.protobuf().standardInitialRequest().build(),
                InitialRequests.protobuf().standardInitialRequest()
                        .setExtension(OpenMrcExtensions.Browser.browser, OpenMrcExtensions.Browser.newBuilder()
                                .setName("Firefox")
                                .setType("Gecko")
                                .setVersion("36")
                                .setMajorVersion("36")
                                .setManufacturer("Mozilla")
                                .build())
                        .build()
        );
    }

    @Test
    public void testSerializeBrowser() {
        OpenMrcExtensions.Browser browser = OpenMrcExtensions.Browser.newBuilder()
                .setName("Firefox")
                .setVersion("36a")
                .setMajorVersion("36")
                .setManufacturer("Mozilla")
                .build();

        String browserAsJson = new JsonFormat().printToString(browser);

        assertThat(browserAsJson, equalTo("{\"name\": \"Firefox\",\"manufacturer\": \"Mozilla\",\"version\": \"36a\",\"majorVersion\": \"36\"}"));
    }

    @Test
    public void testParseBrowser() throws JsonFormat.ParseException {
        OpenMrcExtensions.Browser.Builder builder = OpenMrcExtensions.Browser.newBuilder();

        String jsonFormat = "{" +
                "\"name\": \"Firefox\"," +
                "\"version\": \"36a\"," +
                "\"majorVersion\": \"36\"," +
                "\"type\": \"Gecko\"," +
                "\"manufacturer\": \"Mozilla\"" +
                "}";

        new JsonFormat().merge(jsonFormat, ExtensionRegistry.getEmptyRegistry(), builder);

        OpenMrcExtensions.Browser browser = builder.build();

        assertThat(browser.getName(), equalTo("Firefox"));
        assertThat(browser.getManufacturer(), equalTo("Mozilla"));
        assertThat(browser.getVersion(), equalTo("36a"));
        assertThat(browser.getMajorVersion(), equalTo("36"));
        assertThat(browser.getType(), equalTo("Gecko"));
    }

}
