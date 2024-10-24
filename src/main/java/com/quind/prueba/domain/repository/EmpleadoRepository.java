package com.quind.prueba.domain.repository;

import com.quind.prueba.domain.model.Empleado;

import java.util.Optional;

public interface EmpleadoRepository {
    Optional<Empleado> findByNumeroDocumento(String documento);
}
