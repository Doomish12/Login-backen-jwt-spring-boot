package com.diego.login.services.impl;

import com.diego.login.dto.SaveUsuario;
import com.diego.login.exception.InvalidPasswordException;
import com.diego.login.exception.registerPersonalizadoException;
import com.diego.login.persistence.entity.Usuario;
import com.diego.login.persistence.interfaces.IUsuario;
import com.diego.login.persistence.repository.UsuarioRepo;
import com.diego.login.persistence.util.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService  implements IUsuario {

    @Autowired
    private UsuarioRepo usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Page<Usuario> findAll(Pageable pageable) {
        return usuarioRepo.findAll(pageable);
    }

    @Override
    public Usuario registrarUsuario(SaveUsuario saveUsuario) {

        //VALIDATE LLAMA AL METODO QUE VALIDA EL PASSWORD 2 VECES
        validatePassword(saveUsuario);

        Usuario usuario = new Usuario();
        usuario.setUsername(saveUsuario.getUsername());
        usuario.setNombre(saveUsuario.getNombre());
        usuario.setApellido(saveUsuario.getApellido());
        usuario.setEmail(saveUsuario.getEmail());
        usuario.setPassword(passwordEncoder.encode(saveUsuario.getPassword()));
        usuario.setRol(Rol.USUARIO);

        return mensajesErrorPersonalizadosRegister(usuario);
    }

    //VALIDACION PERSONALIZADA PARA EL REGISTER USUARIO
    private Usuario mensajesErrorPersonalizadosRegister(Usuario usuario) {
        try {
            return usuarioRepo.save(usuario);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = e.getRootCause().getMessage();
            if (errorMessage.contains("Duplicate entry")) {
                String[] parts = errorMessage.split("Duplicate entry '")[1].split("' for key '");
                String duplicateValue = parts[0]; // Extraer el valor duplicado
                String constraint = parts[1]; // Extraer el nombre de la constraint


                String campoDuplicado = "valor";
                if (constraint.contains("tb_usuario.UKspmnyb4dsul95fjmr5kmdmvub")) {
                    campoDuplicado = "email";
                } else if (constraint.contains("tb_usuario.UKplh3sd5xqp709wamcutkiq85m")) {
                    campoDuplicado = "username";
                }

                throw new registerPersonalizadoException("Ya existe un usuario con el " + campoDuplicado + " '" + duplicateValue + "'");
            }
         throw new registerPersonalizadoException("No se pudo registrar ERROR, Comuníquece con Administrador.");
        }
    }

    //VALIDACION DE PASSWORD PARA 2 VECES ESCRIBIRLA
    private void validatePassword(SaveUsuario dto) {

        if(!StringUtils.hasText(dto.getPassword()) || !StringUtils.hasText(dto.getRepeatPassword())){
            throw  new InvalidPasswordException("Las contraseñas no coinciden");
        }

        if(!dto.getPassword().equals(dto.getRepeatPassword())){
            throw  new InvalidPasswordException("Las contraseñas no coinciden");
        }
    }

}
