package com.quind.prueba.application.services.impl;

import com.quind.prueba.domain.dto.CrearSolicitud;
import com.quind.prueba.domain.model.enums.EstadoSolicitudEnum;
import com.quind.prueba.infrastructure.configuration.constans.Constantes;
import com.quind.prueba.infrastructure.configuration.ex.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class ReglaNegocioSolicitud {
    public void validarFechaSolicitud(CrearSolicitud solicitud) {
        LocalDate fechaActual = LocalDate.now();


        if (solicitud.getTipoSolicitud() == null ) {
            throw new BusinessException(Constantes.TIPO_SOLICITUD_NO_NULL);
        }

        String tipoSolicitud = solicitud.getTipoSolicitud();

        if (tipoSolicitud.equals("VC") || tipoSolicitud.equals("DNR")) {
            if (ChronoUnit.MONTHS.between(solicitud.getFechaSolicitud(), fechaActual) < 1) {
                throw new BusinessException(Constantes.VALIDACION_VC_DNR);
            }
        } else if (tipoSolicitud.equals("AU")) {
            long time = ChronoUnit.DAYS.between(solicitud.getFechaSolicitud(), fechaActual);
            log.info(String.valueOf(time));

            if (ChronoUnit.DAYS.between(solicitud.getFechaSolicitud(), fechaActual) < 2) {
                throw new BusinessException(Constantes.VALIDACION_AU);
            }
        }
    }

    public void validarCambioEstado(String comentario, EstadoSolicitudEnum nuevoEstado) {
        if ((nuevoEstado == EstadoSolicitudEnum.APROBADA || nuevoEstado == EstadoSolicitudEnum.DENEGADA)
                && (comentario == null || comentario.isEmpty())) {
            throw new BusinessException(Constantes.VALIDACION_CAMBIO_ESTADO_AP_DN);
        }
    }
}
