package com.example.turmas.service;

import com.example.turmas.api.dto.TurmaDto;

import java.util.List;

public interface TurmaService {

    TurmaDto criar(TurmaDto dto);

    TurmaDto buscarPorId(Long id);

    List<TurmaDto> buscarTodas();

    TurmaDto buscarPorNome(String nome);

    List<TurmaDto> buscarPorPeriodo(String periodo);

    List<TurmaDto> buscarPorCurso(String curso);

    void deletar(Long id);
}
