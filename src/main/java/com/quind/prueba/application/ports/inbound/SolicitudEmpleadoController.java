package com.quind.prueba.application.ports.inbound;

import com.quind.prueba.application.services.impl.SolicitudEmpleadoServiceImpl;
import com.quind.prueba.domain.dto.ActualizarEstado;
import com.quind.prueba.domain.dto.CrearSolicitud;
import com.quind.prueba.domain.dto.RespuestaCrearSolicitud;
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
    public ResponseEntity<RespuestaCrearSolicitud> crearSolicitud(@RequestBody CrearSolicitud solicitud) {
        RespuestaCrearSolicitud nuevaSolicitud = solicitudEmpleadoService.crearSolicitud(solicitud);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaSolicitud);
    }

    @GetMapping
    public ResponseEntity<List<SolicitudEmpleado>> obtenerSolicitudes(
            @RequestParam(required = false) String tipoDocumento,
            @RequestParam(required = false) String numeroDocumento) {

        List<SolicitudEmpleado> solicitudes = solicitudEmpleadoService.obtenerSolicitudes(tipoDocumento, numeroDocumento);
        return ResponseEntity.ok(solicitudes);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SolicitudEmpleado>> findAll() {

        List<SolicitudEmpleado> solicitudes = solicitudEmpleadoService.findAll();
        return ResponseEntity.ok(solicitudes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RespuestaCrearSolicitud> actualizarSolicitud(@PathVariable Long id,
                                                                 @RequestBody ActualizarEstado solicitudActualizada) {
        RespuestaCrearSolicitud solicitud = solicitudEmpleadoService.actualizarSolicitud(id, solicitudActualizada);
        return ResponseEntity.ok(solicitud);
    }
}
