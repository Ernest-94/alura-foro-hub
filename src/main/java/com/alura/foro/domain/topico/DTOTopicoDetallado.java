package com.alura.foro.domain.topico;

import java.time.LocalDateTime;

public record DTOTopicoDetallado (Long id, String titulo, String mensaje, LocalDateTime fechaCreacion,
                                  String status, String nombreAutor, String nombreCurso){
    public DTOTopicoDetallado(Topico topico){
        this(
                topico.getId(),topico.getTitulo(),topico.getMensaje(),topico.getFechaCreacion(),
                topico.getStatus().toString(),topico.getAutor().getNombre(),topico.getCurso().getNombre()
        );
    }
}
