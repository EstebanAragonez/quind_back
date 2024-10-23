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

    @ManyToOne
    @JoinColumn(name = "id_solicitud", nullable = false)
    private TipoSolicitud tipoSolicitud;

    @ManyToOne
    @JoinColumn(name = "id_documento", nullable = false)
    private Documento documento;

    @Column(name = "numero_documento", nullable = false)
    private String numeroDocumento;

    @Column(name = "nombre_empleado", nullable = false)
    private String nombreEmpleado;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoSolicitudEnum estado;

    @Column(name = "comentarios")
    private String comentarios;
}
