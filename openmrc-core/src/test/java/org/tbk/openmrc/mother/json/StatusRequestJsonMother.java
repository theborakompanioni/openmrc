package org.tbk.openmrc.mother.json;

/**
 * Created by void on 19.06.15.
 */
public class StatusRequestJsonMother {

    public String standardStatusRequest() {
        return "{\n" +
                "  \"monitorId\": \"5de8c5b5-8c40-4d62-abd8-17b942e33d0b\",\n" +
                "  \"sessionId\": \"41036669-c0e8-44bf-ac4c-0016906d6b13\",\n" +
                "  \"type\": \"STATUS\",\n" +
                "  \"status\": {\n" +
                "    \"test\": {\n" +
                "      \"monitorState\": {\n" +
                "        \"code\": 2,\n" +
                "        \"state\": \"fullyvisible\",\n" +
                "        \"percentage\": 1,\n" +
                "        \"previous\": {\n" +
                "          \"code\": 2,\n" +
                "          \"state\": \"fullyvisible\",\n" +
                "          \"percentage\": 1,\n" +
                "          \"fullyvisible\": true,\n" +
                "          \"visible\": true,\n" +
                "          \"hidden\": false\n" +
                "        },\n" +
                "        \"fullyvisible\": true,\n" +
                "        \"visible\": true,\n" +
                "        \"hidden\": false\n" +
                "      },\n" +
                "      \"testConfig\": {\n" +
                "        \"percentageLimit\": 0.5,\n" +
                "        \"timeLimit\": 1000,\n" +
                "        \"interval\": 100\n" +
                "      },\n" +
                "      \"timeReport\": {\n" +
                "        \"timeStarted\": 0,\n" +
                "        \"timeHidden\": 0,\n" +
                "        \"timeVisible\": 999,\n" +
                "        \"timeFullyVisible\": 999,\n" +
                "        \"timeRelativeVisible\": 999,\n" +
                "        \"duration\": 999,\n" +
                "        \"percentage\": {\n" +
                "          \"current\": 1,\n" +
                "          \"maximum\": 1,\n" +
                "          \"minimum\": 1\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"viewport\": {\n" +
                "    \"width\": 1920,\n" +
                "    \"height\": 911\n" +
                "  }\n" +
                "}";
    }
}
