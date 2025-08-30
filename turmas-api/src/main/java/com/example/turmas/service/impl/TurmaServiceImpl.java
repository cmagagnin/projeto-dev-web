package com.example.turmas.service.impl;

import com.example.turmas.api.dto.TurmaDto;
import com.example.turmas.domain.Turma;
import com.example.turmas.domain.TurmaRepository;
import com.example.turmas.service.TurmaService;
import com.example.turmas.shared.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TurmaServiceImpl implements TurmaService {

    private final TurmaRepository repository;

    @Override
    public TurmaDto criar(TurmaDto dto) {
        Turma turma = toEntity(dto);
        turma.setId(null); // garante insert
        Turma salvo = repository.save(turma);
        return toDto(salvo);
    }

    @Override
    public TurmaDto buscarPorId(Long id) {
        Turma turma = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada para id=" + id));
        return toDto(turma);
    }

    @Override
    public List<TurmaDto> buscarTodas() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public TurmaDto buscarPorNome(String nome) {
        Turma turma = repository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada para nome=" + nome));
        return toDto(turma);
    }

    @Override
    public List<TurmaDto> buscarPorPeriodo(String periodo) {
        return repository.findByPeriodoIgnoreCase(periodo).stream().map(this::toDto).toList();
    }

    @Override
    public List<TurmaDto> buscarPorCurso(String curso) {
        return repository.findByCursoIgnoreCase(curso).stream().map(this::toDto).toList();
    }

    @Override
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Turma não encontrada para id=" + id);
        }
        repository.deleteById(id);
    }

    // ====== Mapeamentos DTO <-> Entidade ======
    private TurmaDto toDto(Turma t) {
        return TurmaDto.builder()
                .id(t.getId())
                .nome(t.getNome())
                .periodo(t.getPeriodo())
                .curso(t.getCurso())
                .build();
    }

    private Turma toEntity(TurmaDto dto) {
        return Turma.builder()
                .id(dto.getId())
                .nome(dto.getNome())
                .periodo(dto.getPeriodo())
                .curso(dto.getCurso())
                .build();
    }
}
