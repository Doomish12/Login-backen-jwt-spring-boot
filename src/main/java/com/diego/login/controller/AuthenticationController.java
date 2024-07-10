package com.diego.login.controller;

import com.diego.login.dto.RegisterUsuario;
import com.diego.login.dto.SaveUsuario;
import com.diego.login.dto.auth.AuthResponse;
import com.diego.login.dto.auth.LoginRequest;
import com.diego.login.services.impl.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @GetMapping("/validar-token")
    public ResponseEntity<AuthResponse> validarToken(@RequestHeader("Authorization") String authorizationHeader) {
        // Verificar que el encabezado Authorization contiene el prefijo "Bearer "
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Extraer el token JWT del encabezado
        String token = authorizationHeader.substring(7);

        AuthResponse authResponse = authService.validateToken(token);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterUsuario> registrarUsuario(@RequestBody @Valid SaveUsuario saveUsuario) {
        RegisterUsuario registerUsuario = authService.registerUsuario(saveUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUsuario);
    }


}
