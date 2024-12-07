package com.alura.foro.domain.topico;

public record DTOTopicoUpdate(
        String titulo,
        String mensaje,
        String status,
        Long cursoId
) {
}
