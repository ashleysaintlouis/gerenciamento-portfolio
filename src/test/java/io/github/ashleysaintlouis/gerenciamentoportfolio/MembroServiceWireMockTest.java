package io.github.ashleysaintlouis.gerenciamentoportfolio;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.github.ashleysaintlouis.gerenciamentoportfolio.configuration.WireMockTestConfig;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroExternalDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroRequestDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.BusinessException;
import io.github.ashleysaintlouis.gerenciamentoportfolio.mapper.MembroMapper;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.MembroService;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.client.ExternalApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.atomic.AtomicLong;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {WireMockTestConfig.class})
class MembroServiceWireMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MembroService membroService;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private MembroMapper membroMapper;

    private final AtomicLong idGenerator = new AtomicLong(1);
    @Autowired
    private ExternalApiService externalApiService;

    @BeforeEach
    void setupMock() {
        wireMockServer.resetAll();

        wireMockServer.stubFor(get(urlPathEqualTo("/valida-cargo"))
                .withQueryParam("cargo", equalTo("funcionario"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));

        wireMockServer.stubFor(get(urlPathEqualTo("/valida-cargo"))
                .withQueryParam("cargo", equalTo("gerente"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("false")));

        wireMockServer.stubFor(get(urlPathEqualTo("/health"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("OK")));
    }



    @Test
    void deveValidarConexaoComApiExterna() {
        String response = externalApiService.testarConexaoApiExterna();
        assertThat(response).isEqualTo("OK");

        wireMockServer.verify(getRequestedFor(urlPathEqualTo("/health")));
    }
}
