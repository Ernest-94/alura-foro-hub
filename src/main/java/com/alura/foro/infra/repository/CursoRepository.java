package com.alura.foro.infra.repository;

import com.alura.foro.domain.curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso,Long> {

}
