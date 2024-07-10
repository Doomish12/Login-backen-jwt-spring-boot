package com.diego.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

@Data
public class SaveUsuario  implements Serializable {


    @NotBlank
    @Size(min = 4, message = "El nombre debe tener al menos 4 caracteres")
    private String nombre;

    @NotBlank
    @Size(min = 1, message = "El apellido debe tener al menos 1 carácter")
    private String apellido;

    @NotBlank
    @Size(min = 4, message = "El nombre de usuario debe tener al menos 4 caracteres")
    private String username;

    @NotBlank
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotBlank
    @Size(min = 8, message = "La contraseña repetida debe tener al menos 8 caracteres")
    private String repeatPassword;

    @NotBlank
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

}
