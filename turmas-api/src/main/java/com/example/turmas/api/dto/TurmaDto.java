package com.example.turmas.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TurmaDto {
    private Long id;

    @NotBlank(message = "O nome da turma não pode ser nulo ou vazio.")
    private String nome;

    private String periodo;

    private String curso;
}
