package com.quind.prueba.infrastructure.repositories;

import com.quind.prueba.domain.model.Documento;
import com.quind.prueba.domain.repository.DocumentoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaDocumentoRepository extends JpaRepository<Documento, Long>, DocumentoRepository {
}
