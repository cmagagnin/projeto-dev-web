package com.example.turmas.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TurmaRepository extends JpaRepository<Turma, Long> {

    Optional<Turma> findByNomeIgnoreCase(String nome);

    List<Turma> findByPeriodoIgnoreCase(String periodo);

    List<Turma> findByCursoIgnoreCase(String curso);
}
