package com.desafio.luizalabs;

import com.github.tomakehurst.wiremock.core.Options;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@ActiveProfiles("test")
@SpringBootTest(
        classes = {Application.class, ConfigurationTests.class},
        properties = "spring.cache.type=NONE"
)
@EnableWebMvc
@AutoConfigureWireMock(port = Options.DYNAMIC_PORT)
@ExtendWith(SpringExtension.class)
@Retention(RUNTIME)
@Target(TYPE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public @interface IntegrationTests {
}
