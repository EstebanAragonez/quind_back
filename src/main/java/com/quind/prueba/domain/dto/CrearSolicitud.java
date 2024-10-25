package com.quind.prueba.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CrearSolicitud {
    private String tipoSolicitud;
    private String numeroDocumento;
    private LocalDate fechaSolicitud;
    private String comentarios;
}
