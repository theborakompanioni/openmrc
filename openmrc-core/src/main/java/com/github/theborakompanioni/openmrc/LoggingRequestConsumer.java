package com.github.theborakompanioni.openmrc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingRequestConsumer implements OpenMrcStorageUnit {

    private static final Logger log = LoggerFactory.getLogger(LoggingRequestConsumer.class);

    @Override
    public void accept(OpenMrc.Request request) {
        log.info("{}", request);
    }
}
