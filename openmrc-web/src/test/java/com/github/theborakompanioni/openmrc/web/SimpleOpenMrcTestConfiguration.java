package com.github.theborakompanioni.openmrc.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
class SimpleOpenMrcTestConfiguration extends WebMvcConfigurerAdapter {
    class SimpleOpenMrcConfiguration extends OpenMrcWebConfigurationSupport {
    }

    @Bean
    public OpenMrcWebConfiguration openMrcWebConfiguration() {
        return new SimpleOpenMrcConfiguration();
    }

    @Bean
    public SimpleOpenMrcCtrl simpleOpenMrcCtrl() {
        return new SimpleOpenMrcCtrl(openMrcWebConfiguration().httpRequestService());
    }

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerModule(jacksonJdk8Module());

        return objectMapper;
    }

    @Bean
    public Jdk8Module jacksonJdk8Module() {
        return new Jdk8Module();
    }

}
