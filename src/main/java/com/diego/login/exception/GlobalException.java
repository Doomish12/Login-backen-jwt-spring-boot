package com.diego.login.exception;

import com.diego.login.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalTime;

@RestControllerAdvice
public class GlobalException {
/**
 * Maneja todas las excepciones genéricas no capturadas por otros manejadores específicos.
 * Devuelve una respuesta con el código de estado HTTP 500 (Internal Server Error)
 * e incluye detalles del error en el cuerpo de la respuesta.
 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception ex, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setBackend_mensaje(ex.getLocalizedMessage());
        apiError.setUrl(request.getRequestURI());
        apiError.setMetodo(request.getMethod());
        apiError.setFecha(LocalTime.now());

        apiError.setMensaje("Error en el servidor intentelo de nuevo");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
/**
 * Maneja excepciones de tipo MethodArgumentNotValidException, que ocurren cuando
 * los argumentos de un método en un controlador no cumplen con las validaciones especificadas.
 * Devuelve una respuesta con el código de estado HTTP 400 (Bad Request)
 * e incluye detalles del error en el cuerpo de la respuesta.
 **/

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setBackend_mensaje(ex.getLocalizedMessage());
        apiError.setUrl(request.getRequestURI());
        apiError.setMetodo(request.getMethod());
        apiError.setFecha(LocalTime.now());

        apiError.setMensaje("Error en la peteción enviada");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }


}
