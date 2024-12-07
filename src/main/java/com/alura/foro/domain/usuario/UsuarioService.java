package com.alura.foro.domain.usuario;

import com.alura.foro.infra.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarUsuario(DTORegistrarUsuario dtoRegistrarUsuario){
        if (usuarioRepository.existsByEmail(dtoRegistrarUsuario.email())){
            throw new IllegalArgumentException("Email ya está en uso.");
        }
        Usuario usuario=new Usuario();
        usuario.setNombre(dtoRegistrarUsuario.nombre());
        usuario.setEmail(dtoRegistrarUsuario.email());
        usuario.setContrasena(passwordEncoder.encode(dtoRegistrarUsuario.contrasena()));

        usuarioRepository.save(usuario);
    }
    public List<DTONombreUsuario> getNombres(){
        return usuarioRepository.getNombres()
                .stream()
                .map(DTONombreUsuario::new)
                .toList();
    }

    public void eliminarUsuarioPorNombre(String nombre) {
        Optional<Usuario> optionalUsuario=usuarioRepository.findByNombre(nombre);
        if (optionalUsuario.isPresent()){
            usuarioRepository.delete(optionalUsuario.get());
        }else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
}
