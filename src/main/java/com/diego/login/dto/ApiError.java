package com.diego.login.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ApiError {

    private String backend_mensaje;

    private String mensaje;

    private String url;

    private String metodo;

    private LocalTime fecha;

}
