package com.quind.prueba.application.services;

import com.quind.prueba.domain.model.Documento;

import java.util.List;

public interface DocumentoService {
    List<Documento> findAll();
}
