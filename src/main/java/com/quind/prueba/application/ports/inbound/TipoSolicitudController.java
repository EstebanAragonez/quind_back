package com.quind.prueba.application.ports.inbound;

import com.quind.prueba.application.services.TipoSolicitudService;
import com.quind.prueba.domain.model.TipoSolicitud;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipo/solicitud")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TipoSolicitudController {
    private final TipoSolicitudService service;

    @GetMapping
    public ResponseEntity<List<TipoSolicitud>> findAll() {

        List<TipoSolicitud> solicitudes = service.findAll();
        return ResponseEntity.ok(solicitudes);
    }
}
