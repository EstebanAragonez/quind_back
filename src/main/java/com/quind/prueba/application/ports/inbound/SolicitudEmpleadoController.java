package com.quind.prueba.application.ports.inbound;

import com.quind.prueba.application.services.impl.SolicitudEmpleadoServiceImpl;
import com.quind.prueba.domain.model.SolicitudEmpleado;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SolicitudEmpleadoController {
    private final SolicitudEmpleadoServiceImpl solicitudEmpleadoService;

    @PostMapping
    public ResponseEntity<SolicitudEmpleado> crearSolicitud(@RequestBody SolicitudEmpleado solicitud) {
        SolicitudEmpleado nuevaSolicitud = solicitudEmpleadoService.crearSolicitud(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSolicitud);
    }

    @GetMapping
    public ResponseEntity<List<SolicitudEmpleado>> obtenerSolicitudes(
            @RequestParam(required = false) String tipoDocumento,
            @RequestParam(required = false) String numeroDocumento) {

        List<SolicitudEmpleado> solicitudes = solicitudEmpleadoService.obtenerSolicitudes(tipoDocumento, numeroDocumento);
        return ResponseEntity.ok(solicitudes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolicitudEmpleado> actualizarSolicitud(@PathVariable Long id,
                                                                 @RequestBody SolicitudEmpleado solicitudActualizada) {
        SolicitudEmpleado solicitud = solicitudEmpleadoService.actualizarSolicitud(id, solicitudActualizada);
        return ResponseEntity.ok(solicitud);
    }
}
