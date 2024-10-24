package com.quind.prueba.infrastructure.configuration.ex;

public class SolicitudNoEncontradaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SolicitudNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}

