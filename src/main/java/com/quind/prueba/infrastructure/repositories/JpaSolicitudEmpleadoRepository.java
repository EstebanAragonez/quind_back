package com.quind.prueba.infrastructure.repositories;

import com.quind.prueba.domain.model.SolicitudEmpleado;
import com.quind.prueba.domain.repository.SolicitudEmpleadoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaSolicitudEmpleadoRepository extends JpaRepository<SolicitudEmpleado, Long>, SolicitudEmpleadoRepository {
    @Override
    List<SolicitudEmpleado> findByDocumento_NombreAndNumeroDocumento(String tipoDocumento, String numeroDocumento);}
