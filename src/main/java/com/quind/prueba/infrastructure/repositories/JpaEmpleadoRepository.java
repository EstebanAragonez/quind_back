package com.quind.prueba.infrastructure.repositories;

import com.quind.prueba.domain.model.Empleado;
import com.quind.prueba.domain.model.TipoSolicitud;
import com.quind.prueba.domain.repository.EmpleadoRepository;
import com.quind.prueba.domain.repository.TipoSolicitudRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaEmpleadoRepository extends JpaRepository<Empleado, Long>, EmpleadoRepository {
    Optional<Empleado> findByNumeroDocumento(String documento);
}
