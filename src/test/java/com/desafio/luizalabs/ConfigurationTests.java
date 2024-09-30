package com.desafio.luizalabs;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestConfiguration
class ConfigurationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Bean
    @Scope("prototype")
    public WireMock wireMock(@Value("${wiremock.server.port}") Integer port) {
        return WireMock.create()
                .port(port)
                .build();
    }

    @Bean
    @Scope("prototype")
    public MockMvc mockMvc() {
        return MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }
}
