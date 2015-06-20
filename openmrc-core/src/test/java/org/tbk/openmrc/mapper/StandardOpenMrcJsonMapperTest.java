package org.tbk.openmrc.mapper;

import com.codahale.metrics.MetricRegistry;
import com.google.protobuf.ExtensionRegistry;
import com.googlecode.protobuf.format.JsonFormat;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.tbk.openmrc.OpenMrc;
import org.tbk.openmrc.OpenMrcExtensions;
import org.tbk.openmrc.mother.InitialRequests;
import org.tbk.openmrc.mother.StatusRequests;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

/**
 * Created by void on 14.06.15.
 */
@RunWith(JUnitParamsRunner.class)
public class StandardOpenMrcJsonMapperTest {

    private StandardOpenMrcJsonMapper mapper;

    @Before
    public void setup() {
        ExtensionRegistry registry = ExtensionRegistry.newInstance();
        registry.add(OpenMrcExtensions.Browser.browser);
        registry.add(OpenMrcExtensions.OperatingSystem.operatingSystem);

        mapper = new StandardOpenMrcJsonMapper(registry, mock(MetricRegistry.class));
    }

    @Test
    public void testParseInitialRequestWithExtensions() throws JsonFormat.ParseException {
        String jsonFormat = InitialRequests.json().initialRequestWithBrowserExtension();

        OpenMrc.Request parsedRequest = mapper.toOpenMrcRequest(jsonFormat).build();
        assertThat(parsedRequest.getInitial().getState().getCode(), is(2));
        assertThat(parsedRequest.getInitial().getState().getState(), is(equalTo(OpenMrc.Visibility.FULLYVISIBLE)));
        assertThat(parsedRequest.getInitial().getState().getPercentage(), is(1.0f));
        assertThat(parsedRequest.getInitial().getState().getFullyvisible(), is(true));
        assertThat(parsedRequest.getInitial().getState().getVisible(), is(true));
        assertThat(parsedRequest.getInitial().getState().getHidden(), is(false));
    }

    @Test
    @Parameters(method = "validRequestJsonParams")
    public void itShouldBeAbleToParseValidRequestJson(String requestJson) throws JsonFormat.ParseException {
        OpenMrc.Request parsedRequest = mapper.toOpenMrcRequest(requestJson).build();
        String parsedRequestJson = mapper.toExchangeRequest(parsedRequest);
        OpenMrc.Request parsedSerializedRequest = mapper.toOpenMrcRequest(parsedRequestJson).build();

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
        OpenMrc.Request parsedRequest = mapper.toOpenMrcRequest(invalidRequestJson).build();
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
        String requestAsJsonString = mapper.toExchangeRequest(request);

        OpenMrc.Request parsedSerializedRequest = mapper.toOpenMrcRequest(requestAsJsonString).build();

        assertThat(parsedSerializedRequest, is(equalTo(request)));
    }

    private List<OpenMrc.Request> requestProtobufParams() {
        return Arrays.asList(
                InitialRequests.protobuf().standardInitialRequest().build(),
                InitialRequests.protobuf().standardInitialRequest()
                        .setExtension(OpenMrcExtensions.Browser.browser, OpenMrcExtensions.Browser.newBuilder()
                                .setName("Firefox")
                                .setVersion("30")
                                .setManufacturer("Mozilla")
                                .build())
                        .build()
        );
    }

    @Test
    public void testSerializeBrowser() {
        OpenMrcExtensions.Browser browser = OpenMrcExtensions.Browser.newBuilder()
                .setName("Firefox")
                .setVersion("30")
                .setManufacturer("Mozilla")
                .build();

        String browserAsJson = JsonFormat.printToString(browser);

        assertThat(browserAsJson, equalTo("{\"version\": \"30\",\"name\": \"Firefox\",\"manufacturer\": \"Mozilla\"}"));
    }

    @Test
    public void testParseBrowser() throws JsonFormat.ParseException {
        OpenMrcExtensions.Browser.Builder builder = OpenMrcExtensions.Browser.newBuilder();

        String jsonFormat = "{" +
                "\"name\": \"Firefox\"," +
                "\"version\": \"36\"," +
                "\"manufacturer\": \"Mozilla\"" +
                "}";

        JsonFormat.merge(jsonFormat, builder);

        OpenMrcExtensions.Browser browser = builder.build();

        assertThat(browser.getName(), equalTo("Firefox"));
        assertThat(browser.getVersion(), equalTo("36"));
        assertThat(browser.getManufacturer(), equalTo("Mozilla"));
    }

}
