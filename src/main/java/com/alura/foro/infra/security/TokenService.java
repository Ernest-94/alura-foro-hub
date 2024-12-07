package com.alura.foro.infra.security;

import com.alura.foro.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String apiSecret;

    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    public String removerBearerDelToken(String token){
        if (token!=null&&token.startsWith("Bearer ")){
            return token.replace("Bearer ","");
        }
        return token;
    }

    public String generarToken(Usuario usuario){
        try {
            Algorithm algorithm=Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("foro")
                    .withSubject(usuario.getEmail())
                    .withClaim("id",usuario.getId())
                    .withClaim("nombre",usuario.getNombre())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        }catch (JWTCreationException exception){
            throw new RuntimeException();
        }
    }

    public String getSubject(String token) {
        if (token==null){
            throw new RuntimeException();
        }
        token=removerBearerDelToken(token);
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("foro")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }
        if (verifier.getSubject()==null){
            throw new RuntimeException("Verifier inválido");
        }
        return verifier.getSubject();
    }

    public String getNombre(String token){
        if(token==null){
            throw new RuntimeException("El token es null");
        }
        token=removerBearerDelToken(token);
        try {
            Algorithm algorithm=Algorithm.HMAC256(apiSecret);
            DecodedJWT verifier=JWT.require(algorithm)
                    .withIssuer("foro")
                    .build()
                    .verify(token);
            String nombre=verifier.getClaim("nombre").asString();
            if (nombre==null||nombre.isEmpty()){
                throw new RuntimeException("Claim 'nombre' existe pero está vacio o es null");
            }
            return nombre;
        }catch (JWTVerificationException exception){
            throw new RuntimeException("Token inválido o expirado", exception);
        }
    }
    public Long getId(String token){
        if(token==null){
            throw new RuntimeException("El token es null");
        }
        token=removerBearerDelToken(token);
        try {
            Algorithm algorithm=Algorithm.HMAC256(apiSecret);
            DecodedJWT verifier=JWT.require(algorithm)
                    .withIssuer("foro")
                    .build()
                    .verify(token);
            Long id=verifier.getClaim("id").asLong();
            if (id==null){
                throw new RuntimeException("Claim 'id' existe pero está vacio o es null");
            }
            return id;
        }catch (JWTVerificationException exception){
            throw new RuntimeException("Token inválido o expirado", exception);
        }
    }
}
