package com.alura.foro.controller;

import com.alura.foro.domain.usuario.DTORegistrarUsuario;
import com.alura.foro.domain.usuario.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registrar")
public class RegistrarUsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity registrarUsuario(@RequestBody @Valid DTORegistrarUsuario dtoRegistrarUsuario){
        usuarioService.registrarUsuario(dtoRegistrarUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado exitosamente.");
    }
}
