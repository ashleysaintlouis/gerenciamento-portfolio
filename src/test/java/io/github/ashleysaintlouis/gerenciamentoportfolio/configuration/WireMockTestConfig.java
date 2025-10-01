package io.github.ashleysaintlouis.gerenciamentoportfolio.configuration;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

@TestConfiguration
public class WireMockTestConfig {

    @Bean
    public WireMockServer wireMockServer() {
        WireMockServer server = new WireMockServer(WireMockConfiguration.options().dynamicPort());
        server.start();
        return server;
    }

    @Bean("wireMockRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

