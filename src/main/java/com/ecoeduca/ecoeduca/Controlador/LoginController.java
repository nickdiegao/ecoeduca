package com.ecoeduca.ecoeduca.Controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecoeduca.ecoeduca.Services.ServiceUser;
import com.ecoeduca.ecoeduca.model.LoginRequest;
import com.ecoeduca.ecoeduca.model.LoginResponse;
import com.ecoeduca.ecoeduca.model.Usuario;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private ServiceUser serviceUser;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = serviceUser.authenticate(loginRequest.getEmail(), loginRequest.getSenha());
        if (usuario != null) {
            return ResponseEntity.ok(new LoginResponse(usuario.getId()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
}

