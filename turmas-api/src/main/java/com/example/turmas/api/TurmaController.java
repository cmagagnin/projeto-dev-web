package com.example.turmas.api;

import com.example.turmas.api.dto.TurmaDto;
import com.example.turmas.service.TurmaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/turmas")
@RequiredArgsConstructor
public class TurmaController {

    private final TurmaService service;

    // Criar Turma (201 + Location)
    @PostMapping
    public ResponseEntity<TurmaDto> criar(@Valid @RequestBody TurmaDto dto,
                                          UriComponentsBuilder uriBuilder) {
        TurmaDto criada = service.criar(dto);
        URI location = uriBuilder.path("/turmas/{id}")
                .buildAndExpand(criada.getId()).toUri();
        return ResponseEntity.created(location).body(criada);
    }

    // Buscar por ID (200 | 404)
    @GetMapping("/{id}")
    public ResponseEntity<TurmaDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // Buscar todas (200, pode lista vazia)
    @GetMapping
    public ResponseEntity<List<TurmaDto>> buscarTodas() {
        return ResponseEntity.ok(service.buscarTodas());
    }

    // Buscar por nome (200 | 404)
    @GetMapping("/nome/{nome}")
    public ResponseEntity<TurmaDto> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    // Buscar por período (200, pode lista vazia)
    @GetMapping("/periodo/{periodo}")
    public ResponseEntity<List<TurmaDto>> buscarPorPeriodo(@PathVariable String periodo) {
        return ResponseEntity.ok(service.buscarPorPeriodo(periodo));
    }

    // Buscar por curso (200, pode lista vazia)
    @GetMapping("/curso/{curso}")
    public ResponseEntity<List<TurmaDto>> buscarPorCurso(@PathVariable String curso) {
        return ResponseEntity.ok(service.buscarPorCurso(curso));
    }

    // Deletar (204 | 404)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
