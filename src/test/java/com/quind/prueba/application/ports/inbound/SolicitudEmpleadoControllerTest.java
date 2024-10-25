package com.quind.prueba.application.ports.inbound;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quind.prueba.application.services.impl.SolicitudEmpleadoServiceImpl;
import com.quind.prueba.domain.dto.ActualizarEstado;
import com.quind.prueba.domain.dto.CrearSolicitud;
import com.quind.prueba.domain.dto.RespuestaCrearSolicitud;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {SolicitudEmpleadoController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SolicitudEmpleadoControllerTest {
    @Autowired
    private SolicitudEmpleadoController solicitudEmpleadoController;

    @MockBean
    private SolicitudEmpleadoServiceImpl solicitudEmpleadoServiceImpl;

    /**
     * Test {@link SolicitudEmpleadoController#crearSolicitud(CrearSolicitud)}.
     * <ul>
     *   <li>Then status {@link StatusResultMatchers#isCreated()}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoController#crearSolicitud(CrearSolicitud)}
     */
    @Test
    @DisplayName("Test crearSolicitud(CrearSolicitud); then status isCreated()")
    void testCrearSolicitud_thenStatusIsCreated() throws Exception {
        // Arrange
        RespuestaCrearSolicitud respuestaCrearSolicitud = new RespuestaCrearSolicitud();
        respuestaCrearSolicitud.setComentarios("Comentarios");
        respuestaCrearSolicitud.setEstado("Estado");
        respuestaCrearSolicitud.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        respuestaCrearSolicitud.setNombreEmpleado("Nombre Empleado");
        respuestaCrearSolicitud.setNumeroDocumento("alice.liddell@example.org");
        respuestaCrearSolicitud.setTipoDocumento("alice.liddell@example.org");
        respuestaCrearSolicitud.setTipoSolicitud("Tipo Solicitud");
        when(solicitudEmpleadoServiceImpl.crearSolicitud(Mockito.<CrearSolicitud>any()))
                .thenReturn(respuestaCrearSolicitud);

        CrearSolicitud crearSolicitud = new CrearSolicitud();
        crearSolicitud.setComentarios("Comentarios");
        crearSolicitud.setFechaSolicitud(null);
        crearSolicitud.setNumeroDocumento("alice.liddell@example.org");
        crearSolicitud.setTipoSolicitud("Tipo Solicitud");
        String content = (new ObjectMapper()).writeValueAsString(crearSolicitud);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(solicitudEmpleadoController)
                .build()
                .perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"tipoSolicitud\":\"Tipo Solicitud\",\"tipoDocumento\":\"alice.liddell@example.org\",\"numeroDocumento\":\"alice"
                                        + ".liddell@example.org\",\"nombreEmpleado\":\"Nombre Empleado\",\"fechaSolicitud\":[1970,1,1],\"estado\":\"Estado"
                                        + "\",\"comentarios\":\"Comentarios\"}"));
    }

    /**
     * Test {@link SolicitudEmpleadoController#obtenerSolicitudes(String, String)}.
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoController#obtenerSolicitudes(String, String)}
     */
    @Test
    @DisplayName("Test obtenerSolicitudes(String, String)")
    void testObtenerSolicitudes() throws Exception {
        // Arrange
        when(solicitudEmpleadoServiceImpl.obtenerSolicitudes(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/solicitudes");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(solicitudEmpleadoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link SolicitudEmpleadoController#findAll()}.
     * <p>
     * Method under test: {@link SolicitudEmpleadoController#findAll()}
     */
    @Test
    @DisplayName("Test findAll()")
    void testFindAll() throws Exception {
        // Arrange
        when(solicitudEmpleadoServiceImpl.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/solicitudes/all");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(solicitudEmpleadoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test
     * {@link SolicitudEmpleadoController#actualizarSolicitud(Long, ActualizarEstado)}.
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoController#actualizarSolicitud(Long, ActualizarEstado)}
     */
    @Test
    @DisplayName("Test actualizarSolicitud(Long, ActualizarEstado)")
    void testActualizarSolicitud() throws Exception {
        // Arrange
        RespuestaCrearSolicitud respuestaCrearSolicitud = new RespuestaCrearSolicitud();
        respuestaCrearSolicitud.setComentarios("Comentarios");
        respuestaCrearSolicitud.setEstado("Estado");
        respuestaCrearSolicitud.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        respuestaCrearSolicitud.setNombreEmpleado("Nombre Empleado");
        respuestaCrearSolicitud.setNumeroDocumento("alice.liddell@example.org");
        respuestaCrearSolicitud.setTipoDocumento("alice.liddell@example.org");
        respuestaCrearSolicitud.setTipoSolicitud("Tipo Solicitud");
        when(solicitudEmpleadoServiceImpl.actualizarSolicitud(Mockito.<Long>any(), Mockito.<ActualizarEstado>any()))
                .thenReturn(respuestaCrearSolicitud);

        ActualizarEstado actualizarEstado = new ActualizarEstado();
        actualizarEstado.setComentarios("Comentarios");
        actualizarEstado.setEstado("Estado");
        String content = (new ObjectMapper()).writeValueAsString(actualizarEstado);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/solicitudes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(solicitudEmpleadoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"tipoSolicitud\":\"Tipo Solicitud\",\"tipoDocumento\":\"alice.liddell@example.org\",\"numeroDocumento\":\"alice"
                                        + ".liddell@example.org\",\"nombreEmpleado\":\"Nombre Empleado\",\"fechaSolicitud\":[1970,1,1],\"estado\":\"Estado"
                                        + "\",\"comentarios\":\"Comentarios\"}"));
    }
}
