package com.quind.prueba.infrastructure.repositories;

import com.quind.prueba.domain.model.TipoSolicitud;
import com.quind.prueba.domain.repository.TipoSolicitudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaTipoSolicitudRepository extends JpaRepository<TipoSolicitud, Long>, TipoSolicitudRepository {
}
