package com.quind.prueba.application.services.impl;

import com.quind.prueba.domain.model.SolicitudEmpleado;
import com.quind.prueba.domain.model.enums.EstadoSolicitudEnum;
import com.quind.prueba.infrastructure.configuration.ex.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class ReglaNegocioSolicitud {

    public void validarFechaSolicitud(SolicitudEmpleado solicitud) {
        LocalDate fechaActual = LocalDate.now();


        if (solicitud.getTipoSolicitud() == null || solicitud.getTipoSolicitud().getNombre() == null) {
            throw new BusinessException("El tipo de solicitud no puede ser nulo.");
        }

        String tipoSolicitud = solicitud.getTipoSolicitud().getNombre();

        if (tipoSolicitud.equals("VC") || tipoSolicitud.equals("DNR")) {
            if (ChronoUnit.MONTHS.between(fechaActual, solicitud.getFechaSolicitud()) < 1) {
                throw new BusinessException("Para el periodo de vacaciones o días no remunerados se requiere al menos 1 mes de anticipación.");
            }
        } else if (tipoSolicitud.equals("AU")) {
            if (ChronoUnit.DAYS.between(fechaActual, solicitud.getFechaSolicitud()) < 2) {
                throw new BusinessException("Para permisos de ausentismo se requiere al menos 2 días de anticipación.");
            }
        }
    }

    public void validarCambioEstado(SolicitudEmpleado solicitud, EstadoSolicitudEnum nuevoEstado) {
        if (solicitud.getEstado() == EstadoSolicitudEnum.DENEGADA && nuevoEstado != EstadoSolicitudEnum.DENEGADA) {
            throw new BusinessException("No se puede reactivar una solicitud denegada.");
        }


        if ((nuevoEstado == EstadoSolicitudEnum.APROBADA || nuevoEstado == EstadoSolicitudEnum.DENEGADA)
                && (solicitud.getComentarios() == null || solicitud.getComentarios().isEmpty())) {
            throw new BusinessException("Las solicitudes aprobadas o denegadas deben tener un comentario.");
        }
    }
}
