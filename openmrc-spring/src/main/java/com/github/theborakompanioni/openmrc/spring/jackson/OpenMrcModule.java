package com.github.theborakompanioni.openmrc.spring.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.theborakompanioni.openmrc.OpenMrc;
import com.github.theborakompanioni.openmrc.json.OpenMrcJsonMapper;

public class OpenMrcModule extends SimpleModule {

    public OpenMrcModule(OpenMrcJsonMapper jsonMapper) {
        super("openmrc");

        addDeserializer(OpenMrc.Request.class, new OpenMrcRequestDeserializer(jsonMapper));

        addSerializer(OpenMrc.Request.class, new OpenMrcRequestSerializer(jsonMapper));
    }
}
