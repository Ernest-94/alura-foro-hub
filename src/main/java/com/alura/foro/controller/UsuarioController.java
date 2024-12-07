package com.alura.foro.controller;

import com.alura.foro.domain.usuario.DTONombreUsuario;
import com.alura.foro.domain.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<DTONombreUsuario>>getNombres(){
        var nombresUsuario=usuarioService.getNombres();
        return ResponseEntity.ok(nombresUsuario);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#id == ((T(com.alura.forum.domain.usuario.Usuario))authentication.principal).nombre")
    public ResponseEntity eliminarUsuario(@PathVariable String id){
        usuarioService.eliminarUsuarioPorNombre(id);
        return ResponseEntity.noContent().build();
    }
}
