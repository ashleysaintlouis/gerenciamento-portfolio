package io.github.ashleysaintlouis.gerenciamentoportfolio.service.client;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroExternalDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ExternalApiService {

    @Autowired
    private RestTemplate restTemplate;

    private final String BASE_URL = "https://68dc1ec67cd1948060a9842b.mockapi.io/api/v1/membros";

    public List<MembroExternalDto> getAllDatas() {
        ResponseEntity<List<MembroExternalDto>> response =
                restTemplate.exchange(
                        BASE_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<MembroExternalDto>>() {}
                );
        return response.getBody();
    }

    public MembroExternalDto getMembroId(String id) {
        return restTemplate.getForObject(BASE_URL + "/{id}", MembroExternalDto.class, id);
    }


    public String testarConexaoApiExterna() {
        try {
            return restTemplate.getForObject(BASE_URL, String.class);
        } catch (Exception e) {
            throw new BusinessException("Não foi possível conectar à API externa: " + e.getMessage());
        }
    }
}

