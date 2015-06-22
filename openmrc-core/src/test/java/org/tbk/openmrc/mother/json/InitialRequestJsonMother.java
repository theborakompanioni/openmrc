package org.tbk.openmrc.mother.json;

/**
 * Created by void on 19.06.15.
 */
public class InitialRequestJsonMother {

    public String standardInitialRequest() {
        return "{\n" +
                "  \"monitorId\": \"5de8c5b5-8c40-4d62-abd8-17b942e33d0b\",\n" +
                "  \"sessionId\": \"41036669-c0e8-44bf-ac4c-0016906d6b13\",\n" +
                "  \"type\": \"INITIAL\",\n" +
                "  \"initial\": {\n" +
                "    \"timeStarted\": 328472573,\n" +
                "    \"state\": {\n" +
                "      \"code\": 2,\n" +
                "      \"state\": \"fullyvisible\",\n" +
                "      \"percentage\": 1,\n" +
                "      \"fullyvisible\": true,\n" +
                "      \"visible\": true,\n" +
                "      \"hidden\": false\n" +
                "    }\n" +
                "  },\n" +
                "  \"viewport\": {\n" +
                "    \"width\": 1920,\n" +
                "    \"height\": 911\n" +
                "  }\n" +
                "}";
    }

    public String initialRequestWithBrowserExtension() {
        return "{\n" +
                "  \"monitorId\": \"5de8c5b5-8c40-4d62-abd8-17b942e33d0b\",\n" +
                "  \"sessionId\": \"41036669-c0e8-44bf-ac4c-0016906d6b13\",\n" +
                "  \"type\": \"INITIAL\",\n" +
                "  \"initial\": {\n" +
                "    \"timeStarted\": 328472573,\n" +
                "    \"state\": {\n" +
                "      \"code\": 2,\n" +
                "      \"state\": \"fullyvisible\",\n" +
                "      \"percentage\": 1,\n" +
                "      \"fullyvisible\": true,\n" +
                "      \"visible\": true,\n" +
                "      \"hidden\": false\n" +
                "    }\n" +
                "  },\n" +
                "  \"viewport\": {\n" +
                "    \"width\": 1920,\n" +
                "    \"height\": 911\n" +
                "  },\n" +
                "  \"org.tbk.openmrc.Browser.browser\": {\n" +
                "    \"name\": \"FIREFOX\",\n" +
                "    \"version\": \"36\",\n" +
                "    \"manufacturer\": \"Mozilla\"\n" +
                "  }\n" +
                "}";
    }
}
