package com.alura.foro.domain.topico;

import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.usuario.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "topicos",indexes = @Index(name = "idx_curso_id",columnList = "curso_id"),uniqueConstraints = {
        @UniqueConstraint(columnNames = {"titulo","mensaje"})
})
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es mandatorio")
    @Size(max = 255,message = "El título no puede exceder los 255 caracteres")
    private String titulo;

    @NotBlank(message = "El mensaje es mandatorio")
    @Size(max = 5000,message = "El mensaje no puede exceder los 5000 caracteres")
    @Column(length = 5000)
    private String mensaje;

    private LocalDateTime fechaCreacion=LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Status status=Status.ABIERTO;

    @ManyToOne
    @JoinColumn(name = "autor_id",nullable = false)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "curso_id", nullable = false) // Ensure every topic has a course
    private Curso curso;
}
