package com.alura.foro.controller;

import com.alura.foro.domain.topico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    TopicoService topicoService;

    @PostMapping
    public ResponseEntity crearTopico(@RequestBody @Valid DTOTopico dtoTopico, @RequestHeader("Authorization")String token){
        Topico topicoCreado=topicoService.crearTopico(dtoTopico,token);

        DTOOutputTopico dtoRespuesta=new DTOOutputTopico(
                topicoCreado.getId(),
                topicoCreado.getTitulo(),
                topicoCreado.getMensaje(),
                topicoCreado.getAutor().getNombre(),
                topicoCreado.getCurso().getNombre(),
                topicoCreado.getCurso().getCategoria()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoRespuesta);
    }

    @GetMapping
    public ResponseEntity<Page<DTOOutputTopico>> getTopicosPaginados(@PageableDefault(page = 0,size = 10)Pageable pageable){
        Page<Topico>topicos=topicoService.getTopicos(pageable);

        Page<DTOOutputTopico>dtoTopicos=topicos.map(DTOOutputTopico::new);
        return ResponseEntity.ok(dtoTopicos);
    }

    @GetMapping("/curso")
    public Page<DTOOutputTopico>buscarTopicosPorCurso(
            @RequestParam Long cursoId,
            @RequestParam int page,
            @RequestParam int size
    ){
        Pageable pageable= PageRequest.of(page,size);
        return topicoService.getTopicosPorCurso(cursoId,pageable);
    }

    @GetMapping("/{id}")
    public DTOTopicoDetallado getDetallesTopico(@PathVariable Long id){
        return topicoService.getTopicoPorId(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTOTopicoDetallado> actualizarTopico(@PathVariable Long id, @RequestBody @Valid DTOTopicoUpdate update,
                                                               @RequestHeader("Authorization")String token){
        DTOTopicoDetallado updateOutput=topicoService.actualizaTopico(id,update,token);
        return ResponseEntity.ok(updateOutput);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarTopico(@PathVariable Long id,@RequestHeader("Authorization")String token){
        topicoService.borrarTopico(id,token);
        return ResponseEntity.noContent().build();
    }
}
