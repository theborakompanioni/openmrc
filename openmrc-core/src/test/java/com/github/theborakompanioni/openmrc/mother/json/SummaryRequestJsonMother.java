package com.github.theborakompanioni.openmrc.mother.json;

/**
 * Created by void on 19.06.15.
 */
public class SummaryRequestJsonMother {

    public String standardSummaryRequest() {
        return "{\n" +
                "  \"monitorId\": \"5de8c5b5-8c40-4d62-abd8-17b942e33d0b\",\n" +
                "  \"sessionId\": \"41036669-c0e8-44bf-ac4c-0016906d6b13\",\n" +
                "  \"type\": \"SUMMARY\",\n" +
                "  \"summary\": {\n" +
                "    \"report\": {\n" +
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
