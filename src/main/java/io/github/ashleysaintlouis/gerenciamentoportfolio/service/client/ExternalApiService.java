package io.github.ashleysaintlouis.gerenciamentoportfolio.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalApiService {
    @Autowired
    private RestTemplate restTemplate;

    public String getAllDatas() {
        String url = "https://68dc1ec67cd1948060a9842b.mockapi.io/api/v1/membros";
        ResponseEntity<String> forEntity =  restTemplate.getForEntity(url, String.class);
        return forEntity.getBody();
    }

    public String getMembroId(String membroId) {
        String url = "https://68dc1ec67cd1948060a9842b.mockapi.io/api/v1/membros/{id}";
        ResponseEntity<String> forEntity =  restTemplate.getForEntity(url, String.class);
        return forEntity.getBody();
    }
}
