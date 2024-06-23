package com.diego.login.dto.auth;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {

    private String username;
    private String password;
}
