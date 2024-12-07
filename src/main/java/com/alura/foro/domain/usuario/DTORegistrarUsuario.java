package com.alura.foro.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DTORegistrarUsuario(@NotBlank String nombre,
                                  @Email @NotBlank String email,
                                  @NotBlank @Size(min = 6) String contrasena) {
}
