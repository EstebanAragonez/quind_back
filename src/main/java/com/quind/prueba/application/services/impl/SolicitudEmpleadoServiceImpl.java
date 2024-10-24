package com.quind.prueba.application.services.impl;

import com.quind.prueba.application.services.SolicitudEmpleadoService;
import com.quind.prueba.domain.model.SolicitudEmpleado;
import com.quind.prueba.domain.model.enums.EstadoSolicitudEnum;
import com.quind.prueba.domain.repository.SolicitudEmpleadoRepository;
import com.quind.prueba.infrastructure.configuration.ex.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class SolicitudEmpleadoServiceImpl implements SolicitudEmpleadoService {

    private final SolicitudEmpleadoRepository solicitudEmpleadoRepository;
    private final ReglaNegocioSolicitud reglaNegocioSolicitud;

    public SolicitudEmpleado crearSolicitud(SolicitudEmpleado solicitud) {
        reglaNegocioSolicitud.validarFechaSolicitud(solicitud);

        solicitud.setEstado(EstadoSolicitudEnum.NUEVA);

        return solicitudEmpleadoRepository.save(solicitud);
    }


    public List<SolicitudEmpleado> obtenerSolicitudes(String tipoDocumento, String numeroDocumento) {
        return solicitudEmpleadoRepository.findByDocumento_NombreAndNumeroDocumento(tipoDocumento, numeroDocumento);
    }

    public SolicitudEmpleado actualizarSolicitud(Long id, SolicitudEmpleado solicitudActualizada) {
        SolicitudEmpleado solicitud = solicitudEmpleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada"));

        reglaNegocioSolicitud.validarCambioEstado(solicitud, solicitudActualizada.getEstado());

        solicitud.setEstado(solicitudActualizada.getEstado());
        solicitud.setComentarios(solicitudActualizada.getComentarios());

        return solicitudEmpleadoRepository.save(solicitud);
    }
}
