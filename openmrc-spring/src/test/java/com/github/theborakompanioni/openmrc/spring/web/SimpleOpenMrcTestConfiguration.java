package com.github.theborakompanioni.openmrc.spring.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.github.theborakompanioni.openmrc.OpenMrcResponseSupplier;
import com.github.theborakompanioni.openmrc.spring.SpringOpenMrcConfigurationSupport;
import com.github.theborakompanioni.openmrc.spring.example.ExampleOpenMrcHttpResponseSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class SimpleOpenMrcTestConfiguration extends WebMvcConfigurerAdapter {
    private class SimpleOpenMrcConfiguration extends SpringOpenMrcConfigurationSupport {
        @Override
        public OpenMrcResponseSupplier openMrcResponseSupplier() {
            return new ExampleOpenMrcHttpResponseSupplier();
        }
    }

    @Bean
    public OpenMrcWebConfiguration openMrcWebConfiguration() {
        return new SimpleOpenMrcConfiguration();
    }

    @Bean
    public SimpleOpenMrcCtrl simpleOpenMrcCtrl() {
        return new SimpleOpenMrcCtrl(openMrcWebConfiguration().openMrcRequestService());
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
