package com.diego.login.dto.auth;

import com.diego.login.persistence.entity.Usuario;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthResponse implements Serializable {

    private Usuario usuario;
    private String token;
}
