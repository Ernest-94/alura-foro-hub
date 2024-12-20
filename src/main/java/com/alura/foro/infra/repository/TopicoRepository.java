package com.alura.foro.infra.repository;

import com.alura.foro.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico,Long> {
    Page<Topico>findByCursoId(Long cursoId,Pageable pageable);
}
