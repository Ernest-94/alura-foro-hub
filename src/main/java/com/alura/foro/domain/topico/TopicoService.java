package com.alura.foro.domain.topico;

import com.alura.foro.domain.curso.Curso;
import com.alura.foro.domain.usuario.Usuario;
import com.alura.foro.infra.repository.CursoRepository;
import com.alura.foro.infra.repository.TopicoRepository;
import com.alura.foro.infra.repository.UsuarioRepository;
import com.alura.foro.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CursoRepository cursoRepository;

    public Topico crearTopico(DTOTopico dtoTopico,String token){
        var curso=cursoRepository.findById(dtoTopico.cursoId());
        if (curso.isEmpty()){
            throw new IllegalArgumentException("Id del curso inválida");
        }
        String nombreUsuario=tokenService.getNombre(token);
        Optional<Usuario> autor=usuarioRepository.findByNombre(nombreUsuario);
        if(autor.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Usuario no encontrado.");
        }
        Topico topico=new Topico();
        topico.setTitulo(dtoTopico.titulo());
        topico.setMensaje(dtoTopico.mensaje());
        topico.setAutor(autor.get());
        topico.setStatus(Status.ABIERTO);
        topico.setFechaCreacion(LocalDateTime.now());
        topico.setCurso(curso.get());
        return topicoRepository.save(topico);
    }

    public Page<Topico> getTopicos(Pageable pageable){
        return topicoRepository.findAll(pageable);
    }

    public Page<DTOOutputTopico>getTopicosPorCurso(Long cursoId,Pageable pageable){
        Optional<Curso>curso=cursoRepository.findById(cursoId);
        if (curso.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Curso no encontrado.");
        }
        Page<Topico>topicos=topicoRepository.findByCursoId(cursoId,pageable);
        return topicos.map(DTOOutputTopico::new);
    }

    public DTOTopicoDetallado getTopicoPorId(Long id){
        Optional<Topico>topico=topicoRepository.findById(id);
        if (topico.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Tópico no encontrado");
        }
        return new DTOTopicoDetallado(topico.get());
    }

    public DTOTopicoDetallado actualizaTopico(Long idTopico,DTOTopicoUpdate update,String token){
        Long usuarioId=tokenService.getId(token);
        Optional<Topico> topico=topicoRepository.findById(idTopico);
        if (topico.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"No se encontró el Tópico");
        }
        var topicoEncontrado=topico.get();
        if (!topicoEncontrado.getAutor().getId().equals(usuarioId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"No está autorizado para modificar este Tópico.");
        }
        if (update.titulo()!=null)topicoEncontrado.setTitulo(update.titulo());
        if (update.mensaje()!=null)topicoEncontrado.setMensaje(update.mensaje());
        if (update.status()!=null){
            try {
                Status status = Status.valueOf(update.status().toUpperCase());
                topicoEncontrado.setStatus(status);
            }catch (IllegalArgumentException e){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Status Inválido");
            }
        }
        if (update.cursoId()!=null){
            Optional<Curso> curso=cursoRepository.findById(update.cursoId());
            if (curso.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Curso no encontrado");
            }
            topicoEncontrado.setCurso(curso.get());
        }
        topicoRepository.save(topicoEncontrado);
        return new DTOTopicoDetallado(topicoEncontrado);
    }


    public void borrarTopico(Long id, String token) {
        Long usuarioId=tokenService.getId(token);
        Optional<Topico> topico=topicoRepository.findById(id);
        if (topico.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Tópico no encontrado");
        }
        if (!topico.get().getAutor().getId().equals(usuarioId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"No tiene autorización para borrar este tópico.");
        }
        topicoRepository.delete(topico.get());
    }
}
