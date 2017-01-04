package com.github.theborakompanioni.openmrc.spring;

import com.codahale.metrics.MetricRegistry;
import com.github.theborakompanioni.openmrc.OpenMrcRequestConsumer;
import com.github.theborakompanioni.openmrc.OpenMrcRequestInterceptor;
import com.github.theborakompanioni.openmrc.OpenMrcResponseSupplier;
import com.github.theborakompanioni.openmrc.spring.example.ExampleOpenMrcHttpResponseSupplier;
import com.github.theborakompanioni.openmrc.spring.mapper.OpenMrcHttpRequestMapper;
import com.github.theborakompanioni.openmrc.spring.web.OpenMrcHttpRequestService;
import com.google.protobuf.ExtensionRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = SpringOpenMrcConfigurationSupportTestIT.SpringOpenMrcConfigurationSupportImpl.class
)
public class SpringOpenMrcConfigurationSupportTestIT {

    @SpringBootApplication
    public static class SpringOpenMrcConfigurationSupportImpl extends SpringOpenMrcConfigurationSupport {
        @Override
        public OpenMrcResponseSupplier openMrcResponseSupplier() {
            return new ExampleOpenMrcHttpResponseSupplier();
        }
    }

    @Autowired
    public ApplicationContext context;

    @Autowired(required = false)
    private List<OpenMrcRequestConsumer> requestConsumers = Collections.emptyList();

    @Autowired(required = false)
    private List<OpenMrcRequestInterceptor<HttpServletRequest>> requestInterceptors = Collections.emptyList();

    @Test
    public void metricsRegistry() throws Exception {
        final MetricRegistry bean = context.getBean(MetricRegistry.class);
        assertThat(bean, is(notNullValue()));
    }

    @Test
    public void extensionRegistry() throws Exception {
        final ExtensionRegistry bean = context.getBean(ExtensionRegistry.class);
        assertThat(bean, is(notNullValue()));
    }

    @Test
    public void httpRequestMapper() throws Exception {
        final OpenMrcHttpRequestMapper bean = context.getBean(OpenMrcHttpRequestMapper.class);
        assertThat(bean, is(notNullValue()));
    }

    @Test
    public void httpRequestService() throws Exception {
        final OpenMrcHttpRequestService bean = context.getBean(OpenMrcHttpRequestService.class);
        assertThat(bean, is(notNullValue()));
    }

    @Test
    public void openMrcRequestConsumer() throws Exception {
        assertThat(requestConsumers, is(notNullValue()));
        assertThat(requestConsumers.isEmpty(), is(false));
    }

    @Test
    public void httpRequestInterceptor() throws Exception {
        assertThat(requestInterceptors, is(notNullValue()));
        assertThat(requestInterceptors.isEmpty(), is(false));
    }
}
