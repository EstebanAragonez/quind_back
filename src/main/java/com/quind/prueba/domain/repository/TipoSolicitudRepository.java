package com.quind.prueba.domain.repository;

import com.quind.prueba.domain.model.TipoSolicitud;

import java.util.List;

public interface TipoSolicitudRepository {
    List<TipoSolicitud> findAll();
}
