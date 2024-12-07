package com.alura.foro.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DTOOutputTopico(
        Long id, String titulo, String mensaje, String nombre, String cursoNombre, String cursoCategoria) {

    public DTOOutputTopico(Topico topico){
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getNombre(),
                topico.getCurso().getNombre(),
                topico.getCurso().getCategoria()
        );
    }
}
