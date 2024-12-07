package com.alura.foro.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DTOTopico(@NotBlank String titulo, @NotBlank String mensaje, @NotNull Long cursoId) {
}
