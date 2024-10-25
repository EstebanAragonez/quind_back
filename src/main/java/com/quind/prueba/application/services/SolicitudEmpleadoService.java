package com.quind.prueba.application.services;

import com.quind.prueba.domain.dto.ActualizarEstado;
import com.quind.prueba.domain.dto.CrearSolicitud;
import com.quind.prueba.domain.dto.RespuestaCrearSolicitud;
import com.quind.prueba.domain.model.SolicitudEmpleado;

import java.util.List;

public interface SolicitudEmpleadoService {
    RespuestaCrearSolicitud crearSolicitud(CrearSolicitud solicitud);
    List<SolicitudEmpleado> obtenerSolicitudes(String tipoDocumento, String numeroDocumento);
    RespuestaCrearSolicitud actualizarSolicitud(Long id, ActualizarEstado solicitudActualizada);
    List<SolicitudEmpleado> findAll();
}
