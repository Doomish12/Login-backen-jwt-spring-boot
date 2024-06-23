package com.diego.login.services.impl;

import com.diego.login.dto.RegisterUsuario;
import com.diego.login.dto.SaveUsuario;
import com.diego.login.dto.auth.AuthResponse;
import com.diego.login.dto.auth.LoginRequest;
import com.diego.login.persistence.entity.Usuario;
import com.diego.login.persistence.repository.UsuarioRepo;
import com.diego.login.services.auth.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;


    public RegisterUsuario registerUsuario(SaveUsuario saveUsuario){

        Usuario usuario = userService.registrarUsuario(saveUsuario);

        RegisterUsuario userDto = new RegisterUsuario();
        userDto.setUsuario(new Usuario()); // Inicializar el objeto Usuario dentro de RegisterUsuario
        userDto.getUsuario().setId(usuario.getId());
        userDto.getUsuario().setUsername(usuario.getUsername());
        userDto.getUsuario().setNombre(usuario.getNombre());
        userDto.getUsuario().setApellido(usuario.getApellido());
        userDto.getUsuario().setEmail(usuario.getEmail());
        userDto.getUsuario().setPassword(usuario.getPassword());
        userDto.getUsuario().setRol(usuario.getRol());

        String jwt = jwtService.getToken(generateExtraClaims(usuario),usuario);

        userDto.setToken(jwt);

        return  userDto;
    }

    /*AGREGAR AL JWT INFORMACIÓN*/
    private Map<String, Object> generateExtraClaims(Usuario user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("nombre",user.getNombre() +" "+ user.getApellido());
        extraClaims.put("rol",user.getRol().name());

        return extraClaims;
    }

    public AuthResponse login(LoginRequest request){

        //** SI LA CONTRASEÑA ES INCORRECTA SALDRA EL ERROR DE CREDECNIALES INVALIDAS
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        //** SI USUARIO ES INCORRECTO SALDRA EL ERROR DE USERNAME NO ENCONTRADO
        UserDetails user = usuarioRepo.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(generateExtraClaims((Usuario) user), user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUsuario((Usuario) user);
        authResponse.setToken(token);

        return  authResponse;
    }

    //METODO PARA VALIDAR TOKEN Get
    public AuthResponse validateToken(String jwt) {

        AuthResponse authResponse = new AuthResponse();
        try{
            String username = jwtService.getUsernameFromToken(jwt);

            // Aquí puedes realizar la lógica para obtener los datos del usuario a partir del username
            Usuario usuario = usuarioRepo.findByUsername(username).orElseThrow();

            authResponse.setUsuario(usuario);
            authResponse.setToken(jwt);
        }
        catch (Exception e){
            System.out.printf(e.getMessage());
        }
        return authResponse;
    }

}
