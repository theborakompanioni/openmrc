package com.github.theborakompanioni.openmrc;

import com.github.theborakompanioni.openmrc.web.OpenMrcHttpRequestService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenMrcTestConfiguration extends SpringOpenMrcConfigurationSupport {

    @Bean
    public OpenMrcTestCtrl openMrcTestCtrl(OpenMrcHttpRequestService openMrcHttpRequestService) {
        return new OpenMrcTestCtrl(openMrcHttpRequestService);
    }
}
