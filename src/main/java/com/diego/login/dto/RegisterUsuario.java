package com.diego.login.dto;

import com.diego.login.persistence.entity.Usuario;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterUsuario implements Serializable {

    private Usuario usuario;
    private String token;


}
