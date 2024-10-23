package com.quind.prueba.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_documento", nullable = false)
    private Documento documento;

    @Column(name = "numero_documento", nullable = false)
    private String numeroDocumento;

    @Column(name = "area", nullable = false)
    private String area;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

}
