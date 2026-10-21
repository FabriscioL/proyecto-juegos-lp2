package com.juegos.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        LOGGER.error("Error no controlado en la aplicación: ", ex);
        model.addAttribute("errorMensaje", ex.getMessage() != null ? ex.getMessage() : "Ocurrió un error inesperado al procesar la solicitud.");
        return "error/500";
    }
}
