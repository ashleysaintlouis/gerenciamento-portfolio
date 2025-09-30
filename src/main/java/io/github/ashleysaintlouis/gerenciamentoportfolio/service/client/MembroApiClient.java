package io.github.ashleysaintlouis.gerenciamentoportfolio.client;

import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

// Mock que simula a chamada a uma API REST externa
@Component
public class MembroApiClient {

    private final Map<Long, Membro> membrosExternos = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Membro criarMembro(MembroDto dto) {
        // Simula uma chamada POST para um serviço externo
        System.out.println("MOCK API: Criando membro externo: " + dto.nome());
        Membro novoMembro = new Membro();
        long id = idGenerator.getAndIncrement();
        novoMembro.setIdExterno(id);
        novoMembro.setNome(dto.nome());
        novoMembro.setCargo(dto.cargo());
        membrosExternos.put(id, novoMembro);
        return novoMembro;
    }

    public Membro buscarMembroPorId(Long id) {
        // Simula uma chamada GET para um serviço externo
        System.out.println("MOCK API: Buscando membro externo por ID: " + id);
        return membrosExternos.get(id);
    }
}