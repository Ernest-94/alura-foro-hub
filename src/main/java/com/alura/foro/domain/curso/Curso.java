package com.alura.foro.domain.curso;

import com.alura.foro.domain.topico.Topico;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío.")
    @Column(unique = true,nullable = false)
    private String nombre;

    @NotBlank(message = "La categoría no puede estar vacía.")
    @Column(unique = true,nullable = false)
    private String categoria;

    @OneToMany(mappedBy = "curso")
    private List<Topico>topicos=new ArrayList<>();

    @Override
    public boolean equals(Object o){
        if (this==o)return true;
        if (o==null||getClass()!=o.getClass())return false;
        Curso curso=(Curso) o;
        return Objects.equals(nombre,curso.nombre)&& Objects.equals(categoria,curso.categoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, categoria);
    }
}
