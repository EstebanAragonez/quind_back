package com.quind.prueba.domain.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RespuestaCrearSolicitud {
    private String tipoSolicitud;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombreEmpleado;
    private LocalDate fechaSolicitud;
    @Enumerated(EnumType.STRING)
    private String estado;
    private String comentarios;
}
