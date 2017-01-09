package com.github.theborakompanioni.openmrc.spring.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.json.OpenMrcJsonMapper;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class OpenMrcRequestDeserializer extends JsonDeserializer<OpenMrc.Request> {

    private final OpenMrcJsonMapper jsonMapper;

    public OpenMrcRequestDeserializer(OpenMrcJsonMapper jsonMapper) {
        this.jsonMapper = requireNonNull(jsonMapper);
    }

    @Override
    public OpenMrc.Request deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return jsonMapper.toOpenMrcRequest(jsonParser.getValueAsString()).blockingSingle().build();
    }
}
