package com.quind.prueba.domain.model;

import com.quind.prueba.domain.model.enums.TipoDocumentoEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documentos")
@Data
@NoArgsConstructor
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDocumentoEnum nombre;
}
