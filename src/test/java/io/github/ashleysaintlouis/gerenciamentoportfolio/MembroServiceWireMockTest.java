package io.github.ashleysaintlouis.gerenciamentoportfolio;


import com.github.tomakehurst.wiremock.WireMockServer;
import io.github.ashleysaintlouis.gerenciamentoportfolio.configuration.WireMockTestConfig;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroRequestDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroResponseDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.service.MembroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

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

    @BeforeEach
    void setupMock() {
        wireMockServer.resetAll();

        wireMockServer.stubFor(get(urlPathEqualTo("/valida-cargo"))
                .withQueryParam("cargo", equalTo("funcionario"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));
    }

    @Test
    void deveCriarMembroComCargoValido() {
        MembroRequestDto dto = new MembroRequestDto( "Ashley", "funcionario");
        MembroResponseDto response = membroService.criarMembro(dto);

        assertThat(response.nome()).isEqualTo("Ashley");
        assertThat(response.cargo()).isEqualTo("funcionario");
    }
}
