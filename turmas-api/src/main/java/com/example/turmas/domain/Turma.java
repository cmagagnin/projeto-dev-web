package com.example.turmas.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "turmas", uniqueConstraints = {
        @UniqueConstraint(name = "uk_turma_nome", columnNames = "nome")
})
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // validação adicional de banco
    private String nome;

    private String periodo;

    private String curso;
}
