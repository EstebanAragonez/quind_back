package com.quind.prueba.application.ports.inbound;

import com.quind.prueba.application.services.DocumentoService;
import com.quind.prueba.domain.model.Documento;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/documento")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DocumentoController {
    private final DocumentoService service;

    @GetMapping
    public ResponseEntity<List<Documento>> findAll() {

        List<Documento> documentos = service.findAll();
        return ResponseEntity.ok(documentos);
    }
}
