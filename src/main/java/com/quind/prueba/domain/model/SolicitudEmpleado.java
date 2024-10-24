package com.quind.prueba.domain.model;

import com.quind.prueba.domain.model.enums.EstadoSolicitudEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "solicitudes_empleados")
@Data
@NoArgsConstructor
public class SolicitudEmpleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tipo_solicitud")
    private String tipoSolicitud;
    @Column(name = "tipo_documento")
    private String tipoDocumento;
    @Column(name = "numero_documento")
    private String numeroDocumento;
    @Column(name = "nombre_empleado")
    private String nombreEmpleado;
    @Column(name = "fecha_solicitud")
    private LocalDate fechaSolicitud;
    @Column(name = "estado")
    private String estado;
    @Column(name = "comentarios")
    private String comentarios;
}
