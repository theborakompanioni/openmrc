package org.tbk.openmrc.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.tbk.openmrc.mother.InitialRequests;
import org.tbk.openmrc.mother.StatusRequests;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SimpleOpenMrcTestConfiguration.class})
@WebAppConfiguration
public class IntegrationTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mockMvc;

    protected String json(Object o) throws IOException {
        return mapper.writeValueAsString(o);
    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testHelloGet() throws Exception {
        mockMvc.perform(get("/test/openmrc/hello")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testHelloPost() throws Exception {
        mockMvc.perform(post("/test/openmrc/hello")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testWithoutRequestBody() throws Exception {
        mockMvc.perform(post("/test/openmrc/consume")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testWithEmptyRequestBody() throws Throwable {
        Map<String, Object> requestBody = ImmutableMap.of();

        mockMvc.perform(post("/test/openmrc/consume")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(contentType)
                .content(json(requestBody)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testWithRequestBodyWithoutTypeInformation() throws Throwable {
        Map<String, Object> requestBody = ImmutableMap.of("test", "Test");

        mockMvc.perform(post("/test/openmrc/consume")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(contentType)
                .content(json(requestBody)))
                .andExpect(status().isBadRequest());

    }

    /*
    @Test
    @Parameters(method = "validRequestJsonParams")
    public void itShouldBeAbleToParseValidRequestJson(String requestJson) throws JsonFormat.ParseException {

    }

    private List<String> validRequestJsonParams() {
        return Arrays.asList(
                InitialRequests.json().standardInitialRequest(),
                StatusRequests.json().standardStatusRequest(),
                InitialRequests.json().initialRequestWithBrowserExtension()
        );
    }*/

    @Test
    public void testWithInitialEventRequestBody() throws Exception {
        String requestJson = InitialRequests.json().standardInitialRequest();

        mockMvc.perform(post("/test/openmrc/consume")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(contentType)
                .content(requestJson))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testWithStatusEventRequestBody() throws Exception {
        String requestJson = StatusRequests.json().standardStatusRequest();

        mockMvc.perform(post("/test/openmrc/consume")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(contentType)
                .content(requestJson))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testWithSummaryEventRequestBody() throws Exception {
        Map<String, Object> requestBody = ImmutableMap.of(
                "type", "summary",
                "sessionId", "1",
                "monitorId", "1",
                "report", ImmutableMap.of(

                )
        );

        mockMvc.perform(post("/test/openmrc/consume")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(contentType)
                .content(json(requestBody)))
                .andExpect(status().isAccepted());
    }
}
