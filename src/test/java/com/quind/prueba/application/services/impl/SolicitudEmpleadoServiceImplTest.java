package com.quind.prueba.application.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.quind.prueba.domain.dto.ActualizarEstado;
import com.quind.prueba.domain.dto.CrearSolicitud;
import com.quind.prueba.domain.dto.RespuestaCrearSolicitud;
import com.quind.prueba.domain.model.Documento;
import com.quind.prueba.domain.model.Empleado;
import com.quind.prueba.domain.model.SolicitudEmpleado;
import com.quind.prueba.domain.model.enums.EstadoSolicitudEnum;
import com.quind.prueba.domain.model.enums.TipoDocumentoEnum;
import com.quind.prueba.domain.repository.DocumentoRepository;
import com.quind.prueba.domain.repository.EmpleadoRepository;
import com.quind.prueba.domain.repository.SolicitudEmpleadoRepository;
import com.quind.prueba.infrastructure.configuration.ex.BusinessException;
import com.quind.prueba.infrastructure.configuration.ex.SolicitudNoEncontradaException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SolicitudEmpleadoServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SolicitudEmpleadoServiceImplTest {
    @MockBean
    private DocumentoRepository documentoRepository;

    @MockBean
    private EmpleadoRepository empleadoRepository;

    @MockBean
    private ReglaNegocioSolicitud reglaNegocioSolicitud;

    @MockBean
    private SolicitudEmpleadoRepository solicitudEmpleadoRepository;

    @Autowired
    private SolicitudEmpleadoServiceImpl solicitudEmpleadoServiceImpl;

    /**
     * Test {@link SolicitudEmpleadoServiceImpl#crearSolicitud(CrearSolicitud)}.
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoServiceImpl#crearSolicitud(CrearSolicitud)}
     */
    @Test
    @DisplayName("Test crearSolicitud(CrearSolicitud)")
    void testCrearSolicitud() {
        // Arrange
        doThrow(new BusinessException("NUEVA")).when(reglaNegocioSolicitud)
                .validarFechaSolicitud(Mockito.<CrearSolicitud>any());

        Documento documento = new Documento();
        documento.setId(1L);
        documento.setNombre(TipoDocumentoEnum.CC);

        Empleado empleado = new Empleado();
        empleado.setArea("Area");
        empleado.setDocumento(documento);
        empleado.setId(1L);
        empleado.setNombreCompleto("alice.liddell@example.org");
        empleado.setNumeroDocumento("alice.liddell@example.org");
        Optional<Empleado> ofResult = Optional.of(empleado);
        when(empleadoRepository.findByNumeroDocumento(Mockito.<String>any())).thenReturn(ofResult);

        Documento documento2 = new Documento();
        documento2.setId(1L);
        documento2.setNombre(TipoDocumentoEnum.CC);
        Optional<Documento> ofResult2 = Optional.of(documento2);
        when(documentoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        CrearSolicitud solicitud = new CrearSolicitud();
        solicitud.setComentarios("Comentarios");
        solicitud.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        solicitud.setNumeroDocumento("alice.liddell@example.org");
        solicitud.setTipoSolicitud("Tipo Solicitud");

        // Act and Assert
        assertThrows(BusinessException.class, () -> solicitudEmpleadoServiceImpl.crearSolicitud(solicitud));
        verify(reglaNegocioSolicitud).validarFechaSolicitud(isA(CrearSolicitud.class));
        verify(documentoRepository).findById(eq(1L));
        verify(empleadoRepository).findByNumeroDocumento(eq("alice.liddell@example.org"));
    }

    /**
     * Test {@link SolicitudEmpleadoServiceImpl#crearSolicitud(CrearSolicitud)}.
     * <ul>
     *   <li>Given {@link EmpleadoRepository}
     * {@link EmpleadoRepository#findByNumeroDocumento(String)} return empty.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoServiceImpl#crearSolicitud(CrearSolicitud)}
     */
    @Test
    @DisplayName("Test crearSolicitud(CrearSolicitud); given EmpleadoRepository findByNumeroDocumento(String) return empty")
    void testCrearSolicitud_givenEmpleadoRepositoryFindByNumeroDocumentoReturnEmpty() {
        // Arrange
        Optional<Empleado> emptyResult = Optional.empty();
        when(empleadoRepository.findByNumeroDocumento(Mockito.<String>any())).thenReturn(emptyResult);
        new BusinessException("Mensaje");

        CrearSolicitud solicitud = new CrearSolicitud();
        solicitud.setComentarios("Comentarios");
        solicitud.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        solicitud.setNumeroDocumento("alice.liddell@example.org");
        solicitud.setTipoSolicitud("Tipo Solicitud");

        // Act and Assert
        assertThrows(BusinessException.class, () -> solicitudEmpleadoServiceImpl.crearSolicitud(solicitud));
        verify(empleadoRepository).findByNumeroDocumento(eq("alice.liddell@example.org"));
    }

    /**
     * Test {@link SolicitudEmpleadoServiceImpl#crearSolicitud(CrearSolicitud)}.
     * <ul>
     *   <li>Then calls {@link Empleado#getDocumento()}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoServiceImpl#crearSolicitud(CrearSolicitud)}
     */
    @Test
    @DisplayName("Test crearSolicitud(CrearSolicitud); then calls getDocumento()")
    void testCrearSolicitud_thenCallsGetDocumento() {
        // Arrange
        Documento documento = new Documento();
        documento.setId(1L);
        documento.setNombre(TipoDocumentoEnum.CC);

        Documento documento2 = new Documento();
        documento2.setId(1L);
        documento2.setNombre(TipoDocumentoEnum.CC);
        Empleado empleado = mock(Empleado.class);
        when(empleado.getNombreCompleto()).thenThrow(new BusinessException("Mensaje"));
        when(empleado.getDocumento()).thenReturn(documento2);
        doNothing().when(empleado).setArea(Mockito.<String>any());
        doNothing().when(empleado).setDocumento(Mockito.<Documento>any());
        doNothing().when(empleado).setId(Mockito.<Long>any());
        doNothing().when(empleado).setNombreCompleto(Mockito.<String>any());
        doNothing().when(empleado).setNumeroDocumento(Mockito.<String>any());
        empleado.setArea("Area");
        empleado.setDocumento(documento);
        empleado.setId(1L);
        empleado.setNombreCompleto("alice.liddell@example.org");
        empleado.setNumeroDocumento("alice.liddell@example.org");
        Optional<Empleado> ofResult = Optional.of(empleado);
        when(empleadoRepository.findByNumeroDocumento(Mockito.<String>any())).thenReturn(ofResult);

        Documento documento3 = new Documento();
        documento3.setId(1L);
        documento3.setNombre(TipoDocumentoEnum.CC);
        Optional<Documento> ofResult2 = Optional.of(documento3);
        when(documentoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        CrearSolicitud solicitud = new CrearSolicitud();
        solicitud.setComentarios("Comentarios");
        solicitud.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        solicitud.setNumeroDocumento("alice.liddell@example.org");
        solicitud.setTipoSolicitud("Tipo Solicitud");

        // Act and Assert
        assertThrows(BusinessException.class, () -> solicitudEmpleadoServiceImpl.crearSolicitud(solicitud));
        verify(empleado).getDocumento();
        verify(empleado).getNombreCompleto();
        verify(empleado).setArea(eq("Area"));
        verify(empleado).setDocumento(isA(Documento.class));
        verify(empleado).setId(eq(1L));
        verify(empleado).setNombreCompleto(eq("alice.liddell@example.org"));
        verify(empleado).setNumeroDocumento(eq("alice.liddell@example.org"));
        verify(documentoRepository).findById(eq(1L));
        verify(empleadoRepository).findByNumeroDocumento(eq("alice.liddell@example.org"));
    }

    /**
     * Test {@link SolicitudEmpleadoServiceImpl#crearSolicitud(CrearSolicitud)}.
     * <ul>
     *   <li>Then return FechaSolicitud toString is {@code 1970-01-01}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoServiceImpl#crearSolicitud(CrearSolicitud)}
     */
    @Test
    @DisplayName("Test crearSolicitud(CrearSolicitud); then return FechaSolicitud toString is '1970-01-01'")
    void testCrearSolicitud_thenReturnFechaSolicitudToStringIs19700101() {
        // Arrange
        SolicitudEmpleado solicitudEmpleado = new SolicitudEmpleado();
        solicitudEmpleado.setComentarios("Comentarios");
        solicitudEmpleado.setEstado("Estado");
        solicitudEmpleado.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        solicitudEmpleado.setId(1L);
        solicitudEmpleado.setNombreEmpleado("Nombre Empleado");
        solicitudEmpleado.setNumeroDocumento("alice.liddell@example.org");
        solicitudEmpleado.setTipoDocumento("alice.liddell@example.org");
        solicitudEmpleado.setTipoSolicitud("Tipo Solicitud");
        when(solicitudEmpleadoRepository.save(Mockito.<SolicitudEmpleado>any())).thenReturn(solicitudEmpleado);
        doNothing().when(reglaNegocioSolicitud).validarFechaSolicitud(Mockito.<CrearSolicitud>any());

        Documento documento = new Documento();
        documento.setId(1L);
        documento.setNombre(TipoDocumentoEnum.CC);

        Empleado empleado = new Empleado();
        empleado.setArea("Area");
        empleado.setDocumento(documento);
        empleado.setId(1L);
        empleado.setNombreCompleto("alice.liddell@example.org");
        empleado.setNumeroDocumento("alice.liddell@example.org");
        Optional<Empleado> ofResult = Optional.of(empleado);
        when(empleadoRepository.findByNumeroDocumento(Mockito.<String>any())).thenReturn(ofResult);

        Documento documento2 = new Documento();
        documento2.setId(1L);
        documento2.setNombre(TipoDocumentoEnum.CC);
        Optional<Documento> ofResult2 = Optional.of(documento2);
        when(documentoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);

        CrearSolicitud solicitud = new CrearSolicitud();
        solicitud.setComentarios("Comentarios");
        LocalDate fechaSolicitud = LocalDate.of(1970, 1, 1);
        solicitud.setFechaSolicitud(fechaSolicitud);
        solicitud.setNumeroDocumento("alice.liddell@example.org");
        solicitud.setTipoSolicitud("Tipo Solicitud");

        // Act
        RespuestaCrearSolicitud actualCrearSolicitudResult = solicitudEmpleadoServiceImpl.crearSolicitud(solicitud);

        // Assert
        verify(reglaNegocioSolicitud).validarFechaSolicitud(isA(CrearSolicitud.class));
        verify(documentoRepository).findById(eq(1L));
        verify(empleadoRepository).findByNumeroDocumento(eq("alice.liddell@example.org"));
        verify(solicitudEmpleadoRepository).save(isA(SolicitudEmpleado.class));
        LocalDate fechaSolicitud2 = actualCrearSolicitudResult.getFechaSolicitud();
        assertEquals("1970-01-01", fechaSolicitud2.toString());
        assertEquals("CC", actualCrearSolicitudResult.getTipoDocumento());
        assertEquals("Comentarios", actualCrearSolicitudResult.getComentarios());
        assertEquals("NUEVA", actualCrearSolicitudResult.getEstado());
        assertEquals("Tipo Solicitud", actualCrearSolicitudResult.getTipoSolicitud());
        assertEquals("alice.liddell@example.org", actualCrearSolicitudResult.getNombreEmpleado());
        assertEquals("alice.liddell@example.org", actualCrearSolicitudResult.getNumeroDocumento());
        assertSame(fechaSolicitud, fechaSolicitud2);
    }

    /**
     * Test {@link SolicitudEmpleadoServiceImpl#obtenerSolicitudes(String, String)}.
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoServiceImpl#obtenerSolicitudes(String, String)}
     */
    @Test
    @DisplayName("Test obtenerSolicitudes(String, String)")
    void testObtenerSolicitudes() {
        // Arrange
        when(
                solicitudEmpleadoRepository.findByTipoDocumentoAndNumeroDocumento(Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new BusinessException("No se encuentran solicitudes con ese numero y tipo de documento"));

        // Act and Assert
        assertThrows(BusinessException.class, () -> solicitudEmpleadoServiceImpl
                .obtenerSolicitudes("alice.liddell@example.org", "alice.liddell@example.org"));
        verify(solicitudEmpleadoRepository).findByTipoDocumentoAndNumeroDocumento(eq("alice.liddell@example.org"),
                eq("alice.liddell@example.org"));
    }

    /**
     * Test {@link SolicitudEmpleadoServiceImpl#obtenerSolicitudes(String, String)}.
     * <ul>
     *   <li>Then return {@link ArrayList#ArrayList()}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoServiceImpl#obtenerSolicitudes(String, String)}
     */
    @Test
    @DisplayName("Test obtenerSolicitudes(String, String); then return ArrayList()")
    void testObtenerSolicitudes_thenReturnArrayList() {
        // Arrange
        SolicitudEmpleado solicitudEmpleado = new SolicitudEmpleado();
        solicitudEmpleado.setComentarios("No se encuentran solicitudes con ese numero y tipo de documento");
        solicitudEmpleado.setEstado("No se encuentran solicitudes con ese numero y tipo de documento");
        solicitudEmpleado.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        solicitudEmpleado.setId(1L);
        solicitudEmpleado.setNombreEmpleado("No se encuentran solicitudes con ese numero y tipo de documento");
        solicitudEmpleado.setNumeroDocumento("alice.liddell@example.org");
        solicitudEmpleado.setTipoDocumento("alice.liddell@example.org");
        solicitudEmpleado.setTipoSolicitud("No se encuentran solicitudes con ese numero y tipo de documento");

        ArrayList<SolicitudEmpleado> solicitudEmpleadoList = new ArrayList<>();
        solicitudEmpleadoList.add(solicitudEmpleado);
        when(
                solicitudEmpleadoRepository.findByTipoDocumentoAndNumeroDocumento(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(solicitudEmpleadoList);

        // Act
        List<SolicitudEmpleado> actualObtenerSolicitudesResult = solicitudEmpleadoServiceImpl
                .obtenerSolicitudes("alice.liddell@example.org", "alice.liddell@example.org");

        // Assert
        verify(solicitudEmpleadoRepository).findByTipoDocumentoAndNumeroDocumento(eq("alice.liddell@example.org"),
                eq("alice.liddell@example.org"));
        assertSame(solicitudEmpleadoList, actualObtenerSolicitudesResult);
    }

    /**
     * Test {@link SolicitudEmpleadoServiceImpl#obtenerSolicitudes(String, String)}.
     * <ul>
     *   <li>Then throw {@link BusinessException}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoServiceImpl#obtenerSolicitudes(String, String)}
     */
    @Test
    @DisplayName("Test obtenerSolicitudes(String, String); then throw BusinessException")
    void testObtenerSolicitudes_thenThrowBusinessException() {
        // Arrange
        when(
                solicitudEmpleadoRepository.findByTipoDocumentoAndNumeroDocumento(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(BusinessException.class, () -> solicitudEmpleadoServiceImpl
                .obtenerSolicitudes("alice.liddell@example.org", "alice.liddell@example.org"));
        verify(solicitudEmpleadoRepository).findByTipoDocumentoAndNumeroDocumento(eq("alice.liddell@example.org"),
                eq("alice.liddell@example.org"));
    }

    /**
     * Test
     * {@link SolicitudEmpleadoServiceImpl#actualizarSolicitud(Long, ActualizarEstado)}.
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoServiceImpl#actualizarSolicitud(Long, ActualizarEstado)}
     */
    @Test
    @DisplayName("Test actualizarSolicitud(Long, ActualizarEstado)")
    void testActualizarSolicitud() {
        // Arrange
        SolicitudEmpleado solicitudEmpleado = mock(SolicitudEmpleado.class);
        when(solicitudEmpleado.getEstado()).thenReturn("Estado");
        doNothing().when(solicitudEmpleado).setComentarios(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setEstado(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setFechaSolicitud(Mockito.<LocalDate>any());
        doNothing().when(solicitudEmpleado).setId(Mockito.<Long>any());
        doNothing().when(solicitudEmpleado).setNombreEmpleado(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setNumeroDocumento(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setTipoDocumento(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setTipoSolicitud(Mockito.<String>any());
        solicitudEmpleado.setComentarios("Comentarios");
        solicitudEmpleado.setEstado("Estado");
        solicitudEmpleado.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        solicitudEmpleado.setId(1L);
        solicitudEmpleado.setNombreEmpleado("Nombre Empleado");
        solicitudEmpleado.setNumeroDocumento("alice.liddell@example.org");
        solicitudEmpleado.setTipoDocumento("alice.liddell@example.org");
        solicitudEmpleado.setTipoSolicitud("Tipo Solicitud");
        Optional<SolicitudEmpleado> ofResult = Optional.of(solicitudEmpleado);
        when(solicitudEmpleadoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        doThrow(new BusinessException("DENEGADA")).when(reglaNegocioSolicitud)
                .validarCambioEstado(Mockito.<String>any(), Mockito.<EstadoSolicitudEnum>any());

        ActualizarEstado solicitudActualizada = new ActualizarEstado();
        solicitudActualizada.setComentarios("Comentarios");
        solicitudActualizada.setEstado("DENEGADA");

        // Act and Assert
        assertThrows(BusinessException.class,
                () -> solicitudEmpleadoServiceImpl.actualizarSolicitud(1L, solicitudActualizada));
        verify(reglaNegocioSolicitud).validarCambioEstado(eq("Comentarios"), eq(EstadoSolicitudEnum.DENEGADA));
        verify(solicitudEmpleado).getEstado();
        verify(solicitudEmpleado).setComentarios(eq("Comentarios"));
        verify(solicitudEmpleado).setEstado(eq("Estado"));
        verify(solicitudEmpleado).setFechaSolicitud(isA(LocalDate.class));
        verify(solicitudEmpleado).setId(eq(1L));
        verify(solicitudEmpleado).setNombreEmpleado(eq("Nombre Empleado"));
        verify(solicitudEmpleado).setNumeroDocumento(eq("alice.liddell@example.org"));
        verify(solicitudEmpleado).setTipoDocumento(eq("alice.liddell@example.org"));
        verify(solicitudEmpleado).setTipoSolicitud(eq("Tipo Solicitud"));
        verify(solicitudEmpleadoRepository).findById(eq(1L));
    }

    /**
     * Test
     * {@link SolicitudEmpleadoServiceImpl#actualizarSolicitud(Long, ActualizarEstado)}.
     * <ul>
     *   <li>Given {@link SolicitudEmpleado} {@link SolicitudEmpleado#getEstado()}
     * return {@code DENEGADA}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoServiceImpl#actualizarSolicitud(Long, ActualizarEstado)}
     */
    @Test
    @DisplayName("Test actualizarSolicitud(Long, ActualizarEstado); given SolicitudEmpleado getEstado() return 'DENEGADA'")
    void testActualizarSolicitud_givenSolicitudEmpleadoGetEstadoReturnDenegada() {
        // Arrange
        SolicitudEmpleado solicitudEmpleado = mock(SolicitudEmpleado.class);
        when(solicitudEmpleado.getEstado()).thenReturn("DENEGADA");
        doNothing().when(solicitudEmpleado).setComentarios(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setEstado(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setFechaSolicitud(Mockito.<LocalDate>any());
        doNothing().when(solicitudEmpleado).setId(Mockito.<Long>any());
        doNothing().when(solicitudEmpleado).setNombreEmpleado(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setNumeroDocumento(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setTipoDocumento(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setTipoSolicitud(Mockito.<String>any());
        solicitudEmpleado.setComentarios("Comentarios");
        solicitudEmpleado.setEstado("Estado");
        solicitudEmpleado.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        solicitudEmpleado.setId(1L);
        solicitudEmpleado.setNombreEmpleado("Nombre Empleado");
        solicitudEmpleado.setNumeroDocumento("alice.liddell@example.org");
        solicitudEmpleado.setTipoDocumento("alice.liddell@example.org");
        solicitudEmpleado.setTipoSolicitud("Tipo Solicitud");
        Optional<SolicitudEmpleado> ofResult = Optional.of(solicitudEmpleado);
        when(solicitudEmpleadoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        ActualizarEstado solicitudActualizada = new ActualizarEstado();
        solicitudActualizada.setComentarios("Comentarios");
        solicitudActualizada.setEstado("Estado");

        // Act and Assert
        assertThrows(BusinessException.class,
                () -> solicitudEmpleadoServiceImpl.actualizarSolicitud(1L, solicitudActualizada));
        verify(solicitudEmpleado).getEstado();
        verify(solicitudEmpleado).setComentarios(eq("Comentarios"));
        verify(solicitudEmpleado).setEstado(eq("Estado"));
        verify(solicitudEmpleado).setFechaSolicitud(isA(LocalDate.class));
        verify(solicitudEmpleado).setId(eq(1L));
        verify(solicitudEmpleado).setNombreEmpleado(eq("Nombre Empleado"));
        verify(solicitudEmpleado).setNumeroDocumento(eq("alice.liddell@example.org"));
        verify(solicitudEmpleado).setTipoDocumento(eq("alice.liddell@example.org"));
        verify(solicitudEmpleado).setTipoSolicitud(eq("Tipo Solicitud"));
        verify(solicitudEmpleadoRepository).findById(eq(1L));
    }

    /**
     * Test
     * {@link SolicitudEmpleadoServiceImpl#actualizarSolicitud(Long, ActualizarEstado)}.
     * <ul>
     *   <li>Then return FechaSolicitud toString is {@code 1970-01-01}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoServiceImpl#actualizarSolicitud(Long, ActualizarEstado)}
     */
    @Test
    @DisplayName("Test actualizarSolicitud(Long, ActualizarEstado); then return FechaSolicitud toString is '1970-01-01'")
    void testActualizarSolicitud_thenReturnFechaSolicitudToStringIs19700101() {
        // Arrange
        SolicitudEmpleado solicitudEmpleado = mock(SolicitudEmpleado.class);
        when(solicitudEmpleado.getComentarios()).thenReturn("Comentarios");
        when(solicitudEmpleado.getNombreEmpleado()).thenReturn("Nombre Empleado");
        when(solicitudEmpleado.getNumeroDocumento()).thenReturn("alice.liddell@example.org");
        when(solicitudEmpleado.getTipoDocumento()).thenReturn("alice.liddell@example.org");
        when(solicitudEmpleado.getTipoSolicitud()).thenReturn("Tipo Solicitud");
        LocalDate ofResult = LocalDate.of(1970, 1, 1);
        when(solicitudEmpleado.getFechaSolicitud()).thenReturn(ofResult);
        when(solicitudEmpleado.getEstado()).thenReturn("Estado");
        doNothing().when(solicitudEmpleado).setComentarios(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setEstado(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setFechaSolicitud(Mockito.<LocalDate>any());
        doNothing().when(solicitudEmpleado).setId(Mockito.<Long>any());
        doNothing().when(solicitudEmpleado).setNombreEmpleado(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setNumeroDocumento(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setTipoDocumento(Mockito.<String>any());
        doNothing().when(solicitudEmpleado).setTipoSolicitud(Mockito.<String>any());
        solicitudEmpleado.setComentarios("Comentarios");
        solicitudEmpleado.setEstado("Estado");
        solicitudEmpleado.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        solicitudEmpleado.setId(1L);
        solicitudEmpleado.setNombreEmpleado("Nombre Empleado");
        solicitudEmpleado.setNumeroDocumento("alice.liddell@example.org");
        solicitudEmpleado.setTipoDocumento("alice.liddell@example.org");
        solicitudEmpleado.setTipoSolicitud("Tipo Solicitud");
        Optional<SolicitudEmpleado> ofResult2 = Optional.of(solicitudEmpleado);

        SolicitudEmpleado solicitudEmpleado2 = new SolicitudEmpleado();
        solicitudEmpleado2.setComentarios("Comentarios");
        solicitudEmpleado2.setEstado("Estado");
        solicitudEmpleado2.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        solicitudEmpleado2.setId(1L);
        solicitudEmpleado2.setNombreEmpleado("Nombre Empleado");
        solicitudEmpleado2.setNumeroDocumento("alice.liddell@example.org");
        solicitudEmpleado2.setTipoDocumento("alice.liddell@example.org");
        solicitudEmpleado2.setTipoSolicitud("Tipo Solicitud");
        when(solicitudEmpleadoRepository.save(Mockito.<SolicitudEmpleado>any())).thenReturn(solicitudEmpleado2);
        when(solicitudEmpleadoRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        doNothing().when(reglaNegocioSolicitud)
                .validarCambioEstado(Mockito.<String>any(), Mockito.<EstadoSolicitudEnum>any());

        ActualizarEstado solicitudActualizada = new ActualizarEstado();
        solicitudActualizada.setComentarios("Comentarios");
        solicitudActualizada.setEstado("DENEGADA");

        // Act
        RespuestaCrearSolicitud actualActualizarSolicitudResult = solicitudEmpleadoServiceImpl.actualizarSolicitud(1L,
                solicitudActualizada);

        // Assert
        verify(reglaNegocioSolicitud).validarCambioEstado(eq("Comentarios"), eq(EstadoSolicitudEnum.DENEGADA));
        verify(solicitudEmpleado).getComentarios();
        verify(solicitudEmpleado, atLeast(1)).getEstado();
        verify(solicitudEmpleado).getFechaSolicitud();
        verify(solicitudEmpleado).getNombreEmpleado();
        verify(solicitudEmpleado).getNumeroDocumento();
        verify(solicitudEmpleado).getTipoDocumento();
        verify(solicitudEmpleado).getTipoSolicitud();
        verify(solicitudEmpleado, atLeast(1)).setComentarios(eq("Comentarios"));
        verify(solicitudEmpleado, atLeast(1)).setEstado(Mockito.<String>any());
        verify(solicitudEmpleado).setFechaSolicitud(isA(LocalDate.class));
        verify(solicitudEmpleado).setId(eq(1L));
        verify(solicitudEmpleado).setNombreEmpleado(eq("Nombre Empleado"));
        verify(solicitudEmpleado).setNumeroDocumento(eq("alice.liddell@example.org"));
        verify(solicitudEmpleado).setTipoDocumento(eq("alice.liddell@example.org"));
        verify(solicitudEmpleado).setTipoSolicitud(eq("Tipo Solicitud"));
        verify(solicitudEmpleadoRepository).findById(eq(1L));
        verify(solicitudEmpleadoRepository).save(isA(SolicitudEmpleado.class));
        LocalDate fechaSolicitud = actualActualizarSolicitudResult.getFechaSolicitud();
        assertEquals("1970-01-01", fechaSolicitud.toString());
        assertEquals("Comentarios", actualActualizarSolicitudResult.getComentarios());
        assertEquals("Estado", actualActualizarSolicitudResult.getEstado());
        assertEquals("Nombre Empleado", actualActualizarSolicitudResult.getNombreEmpleado());
        assertEquals("Tipo Solicitud", actualActualizarSolicitudResult.getTipoSolicitud());
        assertEquals("alice.liddell@example.org", actualActualizarSolicitudResult.getNumeroDocumento());
        assertEquals("alice.liddell@example.org", actualActualizarSolicitudResult.getTipoDocumento());
        assertSame(ofResult, fechaSolicitud);
    }

    /**
     * Test
     * {@link SolicitudEmpleadoServiceImpl#actualizarSolicitud(Long, ActualizarEstado)}.
     * <ul>
     *   <li>Then throw {@link SolicitudNoEncontradaException}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link SolicitudEmpleadoServiceImpl#actualizarSolicitud(Long, ActualizarEstado)}
     */
    @Test
    @DisplayName("Test actualizarSolicitud(Long, ActualizarEstado); then throw SolicitudNoEncontradaException")
    void testActualizarSolicitud_thenThrowSolicitudNoEncontradaException() {
        // Arrange
        Optional<SolicitudEmpleado> emptyResult = Optional.empty();
        when(solicitudEmpleadoRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        ActualizarEstado solicitudActualizada = new ActualizarEstado();
        solicitudActualizada.setComentarios("Comentarios");
        solicitudActualizada.setEstado("Estado");

        // Act and Assert
        assertThrows(SolicitudNoEncontradaException.class,
                () -> solicitudEmpleadoServiceImpl.actualizarSolicitud(1L, solicitudActualizada));
        verify(solicitudEmpleadoRepository).findById(eq(1L));
    }
}
