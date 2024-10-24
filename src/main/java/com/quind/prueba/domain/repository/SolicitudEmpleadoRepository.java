package com.quind.prueba.domain.repository;

import com.quind.prueba.domain.model.SolicitudEmpleado;

import java.util.List;
import java.util.Optional;

public interface SolicitudEmpleadoRepository {
    SolicitudEmpleado save(SolicitudEmpleado solicitud);
    List<SolicitudEmpleado> findByDocumento_NombreAndNumeroDocumento(String documento, String numeroDocumento);
    Optional<SolicitudEmpleado> findById(Long id);
}
