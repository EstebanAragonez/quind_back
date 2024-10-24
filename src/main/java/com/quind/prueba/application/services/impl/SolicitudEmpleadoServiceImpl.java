package com.quind.prueba.application.services.impl;

import com.quind.prueba.application.services.SolicitudEmpleadoService;
import com.quind.prueba.domain.dto.ActualizarEstado;
import com.quind.prueba.domain.dto.CrearSolicitud;
import com.quind.prueba.domain.dto.RespuestaCrearSolicitud;
import com.quind.prueba.domain.model.Documento;
import com.quind.prueba.domain.model.Empleado;
import com.quind.prueba.domain.model.SolicitudEmpleado;
import com.quind.prueba.domain.model.enums.EstadoSolicitudEnum;
import com.quind.prueba.domain.repository.DocumentoRepository;
import com.quind.prueba.domain.repository.EmpleadoRepository;
import com.quind.prueba.domain.repository.SolicitudEmpleadoRepository;
import com.quind.prueba.infrastructure.configuration.constans.Constantes;
import com.quind.prueba.infrastructure.configuration.ex.BusinessException;
import com.quind.prueba.infrastructure.configuration.ex.SolicitudNoEncontradaException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SolicitudEmpleadoServiceImpl implements SolicitudEmpleadoService {

    private final SolicitudEmpleadoRepository solicitudEmpleadoRepository;
    private final ReglaNegocioSolicitud reglaNegocioSolicitud;
    private final EmpleadoRepository empleadoRepository;
    private final DocumentoRepository documentoRepository;

    public RespuestaCrearSolicitud crearSolicitud(CrearSolicitud solicitud) {
        Optional<Empleado> empleado = empleadoRepository.findByNumeroDocumento(solicitud.getNumeroDocumento());
        if (empleado.isEmpty()) {
            throw new BusinessException(Constantes.EMPLEADO_NO_ENCONTRADO);
        }
        Optional<Documento> documento = documentoRepository.findById(empleado.get().getDocumento().getId());
        if (documento.isEmpty()) {
            throw new BusinessException("El empleado no cuenta con un tipo de documento");
        }
        log.info(documento.get().getNombre().toString());
        RespuestaCrearSolicitud resp = new RespuestaCrearSolicitud();
        SolicitudEmpleado solicitudEmpleado = new SolicitudEmpleado();
        solicitudEmpleado.setTipoDocumento(documento.get().getNombre().toString());
        solicitudEmpleado.setEstado("NUEVO");
        solicitudEmpleado.setNombreEmpleado(empleado.get().getNombreCompleto());
        BeanUtils.copyProperties(solicitud, solicitudEmpleado);
        BeanUtils.copyProperties(solicitudEmpleado, resp);
        reglaNegocioSolicitud.validarFechaSolicitud(solicitud);

        solicitudEmpleadoRepository.save(solicitudEmpleado);
        return resp ;
    }


    public List<SolicitudEmpleado> obtenerSolicitudes(String tipoDocumento, String numeroDocumento) {

        return solicitudEmpleadoRepository.findByTipoDocumentoAndNumeroDocumento(tipoDocumento, numeroDocumento);
    }
    public List<SolicitudEmpleado> findAll() {

        return solicitudEmpleadoRepository.findAll();
    }

    public RespuestaCrearSolicitud actualizarSolicitud(Long id, ActualizarEstado solicitudActualizada) {
        SolicitudEmpleado solicitud = solicitudEmpleadoRepository.findById(id)
                .orElseThrow(() -> new SolicitudNoEncontradaException("Solicitud no encontrada"));
        if ("DENEGADA".equalsIgnoreCase(solicitud.getEstado())) {
            throw new BusinessException("No se puede reactivar una solicitud denegada, cree nuevamente la solicitud");
        }
        RespuestaCrearSolicitud respuestaCrearSolicitud = new RespuestaCrearSolicitud();
        reglaNegocioSolicitud.validarCambioEstado(solicitudActualizada.getComentarios(), EstadoSolicitudEnum.valueOf(solicitudActualizada.getEstado()));

        solicitud.setEstado(solicitudActualizada.getEstado());
        solicitud.setComentarios(solicitudActualizada.getComentarios());
        solicitudEmpleadoRepository.save(solicitud);
        BeanUtils.copyProperties(solicitud, respuestaCrearSolicitud);

        return respuestaCrearSolicitud;
    }
}
