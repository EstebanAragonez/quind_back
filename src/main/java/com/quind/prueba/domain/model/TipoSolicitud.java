package com.quind.prueba.domain.model;

import com.quind.prueba.domain.model.enums.TipoSolicitudEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tipo_solicitudes")
@Data
@NoArgsConstructor
public class TipoSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSolicitudEnum nombre;
}
