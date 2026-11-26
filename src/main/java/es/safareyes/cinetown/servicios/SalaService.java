package es.safareyes.cinetown.servicios;


import es.safareyes.cinetown.dto.SalaDTO;
import es.safareyes.cinetown.mapper.SalaMapper;
import es.safareyes.cinetown.repositorios.ISalasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SalaService {
    private final ISalasRepository salasRepository;
    private final SalaMapper salaMapper;

    public List<SalaDTO> listar() {
        return salaMapper.toDtoList(salasRepository.findAllByOrderByNumeroSalaAsc());
    }

}
