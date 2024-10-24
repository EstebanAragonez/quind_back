package com.quind.prueba.domain.repository;

import com.quind.prueba.domain.model.Documento;

import java.util.List;
import java.util.Optional;

public interface DocumentoRepository {
    Optional<Documento> findById(Long id);

    List<Documento> findAll();
}
