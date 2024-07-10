package com.diego.login.controller;

import com.diego.login.persistence.entity.Usuario;
import com.diego.login.services.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@RestController
@RequestMapping("/user")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class UsuarioController {

    @Autowired
    private UserService userService;


    @GetMapping("/listaUsers")
    public ResponseEntity<Page<Usuario>> listarUsuarios(Pageable pageable) {
        Page<Usuario> usuarios = userService.findAll(pageable);
        if(usuarios.hasContent()){
            return ResponseEntity.ok(usuarios);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


}
