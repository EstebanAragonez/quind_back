package com.quind.prueba.application.services;

import com.quind.prueba.domain.model.SolicitudEmpleado;
import com.quind.prueba.domain.model.enums.EstadoSolicitudEnum;

import java.util.List;

public interface SolicitudEmpleadoService {
    public SolicitudEmpleado crearSolicitud(SolicitudEmpleado solicitud);
    public List<SolicitudEmpleado> obtenerSolicitudes(String tipoDocumento, String numeroDocumento);
    public SolicitudEmpleado actualizarSolicitud(Long id, SolicitudEmpleado solicitudActualizada);
}
