package com.github.theborakompanioni.openmrc.spring.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.json.OpenMrcJsonMapper;

import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class OpenMrcRequestSerializer extends JsonSerializer<OpenMrc.Request> {

    private final OpenMrcJsonMapper jsonMapper;

    public OpenMrcRequestSerializer(OpenMrcJsonMapper jsonMapper) {
        this.jsonMapper = requireNonNull(jsonMapper);
    }

    @Override
    public void serialize(OpenMrc.Request request, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeString(jsonMapper.toJson(request));
    }
}
