package com.quind.prueba.application.services.impl;

import com.quind.prueba.application.services.TipoSolicitudService;
import com.quind.prueba.domain.model.TipoSolicitud;
import com.quind.prueba.domain.repository.TipoSolicitudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoSolicitudServiceImpl implements TipoSolicitudService {

    private final TipoSolicitudRepository tipoSolicitudRepository;

    @Override
    public List<TipoSolicitud> findAll() {
        return tipoSolicitudRepository.findAll();
    }
}
