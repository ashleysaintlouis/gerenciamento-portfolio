package io.github.ashleysaintlouis.gerenciamentoportfolio.service.client;

import io.github.ashleysaintlouis.gerenciamentoportfolio.configuration.RestTemplateConfig;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.ListaMembroDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.dto.membro.MembroRequestDto;
import io.github.ashleysaintlouis.gerenciamentoportfolio.model.Membro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


@Component
public class MembroApiClient {

    private final Map<Long, Membro> membrosExternos = new ConcurrentHashMap<>();
    private final Map<Long, List<Membro>> membrosExternosLista = new ConcurrentHashMap<>();

    private final AtomicLong idGenerator = new AtomicLong(1);

    public Membro criarMembro(MembroRequestDto dto) {
        System.out.println("MOCK API: Criando membro externo: " + dto.nome());
        Membro novoMembro = new Membro();
        long id = idGenerator.getAndIncrement();
        novoMembro.setIdExterno(id);
        novoMembro.setNome(dto.nome());
        novoMembro.setCargo(dto.cargo());
        membrosExternos.put(id, novoMembro);
        return novoMembro;
    }

    public List<Membro> criarListaMembro(ListaMembroDto dto) {
        System.out.println("MOCK API: Criando lista de membros externos: " + dto.membros());

        List<Membro> novosMembros = dto.membros().stream().map(membroDto -> {
            Membro membro = new Membro();
            long id = idGenerator.getAndIncrement();
            membro.setIdExterno(id);
            membro.setNome(membroDto.nome());
            membro.setCargo(membroDto.cargo());
            membrosExternos.put(id, membro);
            return membro;
        }).collect(Collectors.toList());

        long listaId = idGenerator.getAndIncrement();
        membrosExternosLista.put(listaId, novosMembros);

        return novosMembros;
    }

    public Membro buscarMembroPorId(Long id) {
        System.out.println("MOCK API: Buscando membro externo por ID: " + id);
        return membrosExternos.get(id);
    }
}