package com.quind.prueba.application.services.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.quind.prueba.domain.dto.CrearSolicitud;
import com.quind.prueba.domain.model.enums.EstadoSolicitudEnum;
import com.quind.prueba.infrastructure.configuration.ex.BusinessException;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ReglaNegocioSolicitud.class})
@ExtendWith(SpringExtension.class)
class ReglaNegocioSolicitudTest {
    @Autowired
    private ReglaNegocioSolicitud reglaNegocioSolicitud;

    /**
     * Test {@link ReglaNegocioSolicitud#validarFechaSolicitud(CrearSolicitud)}.
     * <ul>
     *   <li>Given {@link BusinessException#BusinessException(String)} with mensaje is
     * {@code VC}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ReglaNegocioSolicitud#validarFechaSolicitud(CrearSolicitud)}
     */
    @Test
    @DisplayName("Test validarFechaSolicitud(CrearSolicitud); given BusinessException(String) with mensaje is 'VC'")
    void testValidarFechaSolicitud_givenBusinessExceptionWithMensajeIsVc() {
        // Arrange
        CrearSolicitud solicitud = mock(CrearSolicitud.class);
        when(solicitud.getFechaSolicitud()).thenThrow(new BusinessException("VC"));
        when(solicitud.getTipoSolicitud()).thenReturn("VC");
        doNothing().when(solicitud).setComentarios(Mockito.<String>any());
        doNothing().when(solicitud).setFechaSolicitud(Mockito.<LocalDate>any());
        doNothing().when(solicitud).setNumeroDocumento(Mockito.<String>any());
        doNothing().when(solicitud).setTipoSolicitud(Mockito.<String>any());
        solicitud.setComentarios("Comentarios");
        solicitud.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        solicitud.setNumeroDocumento("alice.liddell@example.org");
        solicitud.setTipoSolicitud("Tipo Solicitud");

        // Act and Assert
        assertThrows(BusinessException.class, () -> reglaNegocioSolicitud.validarFechaSolicitud(solicitud));
        verify(solicitud).getFechaSolicitud();
        verify(solicitud, atLeast(1)).getTipoSolicitud();
        verify(solicitud).setComentarios(eq("Comentarios"));
        verify(solicitud).setFechaSolicitud(isA(LocalDate.class));
        verify(solicitud).setNumeroDocumento(eq("alice.liddell@example.org"));
        verify(solicitud).setTipoSolicitud(eq("Tipo Solicitud"));
    }

    /**
     * Test {@link ReglaNegocioSolicitud#validarFechaSolicitud(CrearSolicitud)}.
     * <ul>
     *   <li>Given now.</li>
     *   <li>When {@link CrearSolicitud} {@link CrearSolicitud#getFechaSolicitud()}
     * return now.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ReglaNegocioSolicitud#validarFechaSolicitud(CrearSolicitud)}
     */
    @Test
    @DisplayName("Test validarFechaSolicitud(CrearSolicitud); given now; when CrearSolicitud getFechaSolicitud() return now")
    void testValidarFechaSolicitud_givenNow_whenCrearSolicitudGetFechaSolicitudReturnNow() {
        // Arrange
        CrearSolicitud solicitud = mock(CrearSolicitud.class);
        when(solicitud.getFechaSolicitud()).thenReturn(LocalDate.now());
        when(solicitud.getTipoSolicitud()).thenReturn("VC");
        doNothing().when(solicitud).setComentarios(Mockito.<String>any());
        doNothing().when(solicitud).setFechaSolicitud(Mockito.<LocalDate>any());
        doNothing().when(solicitud).setNumeroDocumento(Mockito.<String>any());
        doNothing().when(solicitud).setTipoSolicitud(Mockito.<String>any());
        solicitud.setComentarios("Comentarios");
        solicitud.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        solicitud.setNumeroDocumento("alice.liddell@example.org");
        solicitud.setTipoSolicitud("Tipo Solicitud");

        // Act and Assert
        assertThrows(BusinessException.class, () -> reglaNegocioSolicitud.validarFechaSolicitud(solicitud));
        verify(solicitud).getFechaSolicitud();
        verify(solicitud, atLeast(1)).getTipoSolicitud();
        verify(solicitud).setComentarios(eq("Comentarios"));
        verify(solicitud).setFechaSolicitud(isA(LocalDate.class));
        verify(solicitud).setNumeroDocumento(eq("alice.liddell@example.org"));
        verify(solicitud).setTipoSolicitud(eq("Tipo Solicitud"));
    }

    /**
     * Test {@link ReglaNegocioSolicitud#validarFechaSolicitud(CrearSolicitud)}.
     * <ul>
     *   <li>Given {@code null}.</li>
     *   <li>When {@link CrearSolicitud} {@link CrearSolicitud#getTipoSolicitud()}
     * return {@code null}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ReglaNegocioSolicitud#validarFechaSolicitud(CrearSolicitud)}
     */
    @Test
    @DisplayName("Test validarFechaSolicitud(CrearSolicitud); given 'null'; when CrearSolicitud getTipoSolicitud() return 'null'")
    void testValidarFechaSolicitud_givenNull_whenCrearSolicitudGetTipoSolicitudReturnNull() {
        // Arrange
        CrearSolicitud solicitud = mock(CrearSolicitud.class);
        when(solicitud.getTipoSolicitud()).thenReturn(null);
        doNothing().when(solicitud).setComentarios(Mockito.<String>any());
        doNothing().when(solicitud).setFechaSolicitud(Mockito.<LocalDate>any());
        doNothing().when(solicitud).setNumeroDocumento(Mockito.<String>any());
        doNothing().when(solicitud).setTipoSolicitud(Mockito.<String>any());
        solicitud.setComentarios("Comentarios");
        solicitud.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        solicitud.setNumeroDocumento("alice.liddell@example.org");
        solicitud.setTipoSolicitud("Tipo Solicitud");

        // Act and Assert
        assertThrows(BusinessException.class, () -> reglaNegocioSolicitud.validarFechaSolicitud(solicitud));
        verify(solicitud).getTipoSolicitud();
        verify(solicitud).setComentarios(eq("Comentarios"));
        verify(solicitud).setFechaSolicitud(isA(LocalDate.class));
        verify(solicitud).setNumeroDocumento(eq("alice.liddell@example.org"));
        verify(solicitud).setTipoSolicitud(eq("Tipo Solicitud"));
    }

    /**
     * Test {@link ReglaNegocioSolicitud#validarFechaSolicitud(CrearSolicitud)}.
     * <ul>
     *   <li>When {@link CrearSolicitud} {@link CrearSolicitud#getTipoSolicitud()}
     * return {@code Tipo Solicitud}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ReglaNegocioSolicitud#validarFechaSolicitud(CrearSolicitud)}
     */
    @Test
    @DisplayName("Test validarFechaSolicitud(CrearSolicitud); when CrearSolicitud getTipoSolicitud() return 'Tipo Solicitud'")
    void testValidarFechaSolicitud_whenCrearSolicitudGetTipoSolicitudReturnTipoSolicitud() {
        // Arrange
        CrearSolicitud solicitud = mock(CrearSolicitud.class);
        when(solicitud.getTipoSolicitud()).thenReturn("Tipo Solicitud");
        doNothing().when(solicitud).setComentarios(Mockito.<String>any());
        doNothing().when(solicitud).setFechaSolicitud(Mockito.<LocalDate>any());
        doNothing().when(solicitud).setNumeroDocumento(Mockito.<String>any());
        doNothing().when(solicitud).setTipoSolicitud(Mockito.<String>any());
        solicitud.setComentarios("Comentarios");
        solicitud.setFechaSolicitud(LocalDate.of(1970, 1, 1));
        solicitud.setNumeroDocumento("alice.liddell@example.org");
        solicitud.setTipoSolicitud("Tipo Solicitud");

        // Act
        reglaNegocioSolicitud.validarFechaSolicitud(solicitud);

        // Assert that nothing has changed
        verify(solicitud, atLeast(1)).getTipoSolicitud();
        verify(solicitud).setComentarios(eq("Comentarios"));
        verify(solicitud).setFechaSolicitud(isA(LocalDate.class));
        verify(solicitud).setNumeroDocumento(eq("alice.liddell@example.org"));
        verify(solicitud).setTipoSolicitud(eq("Tipo Solicitud"));
    }

    /**
     * Test
     * {@link ReglaNegocioSolicitud#validarCambioEstado(String, EstadoSolicitudEnum)}.
     * <ul>
     *   <li>When empty string.</li>
     *   <li>Then throw {@link BusinessException}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ReglaNegocioSolicitud#validarCambioEstado(String, EstadoSolicitudEnum)}
     */
    @Test
    @DisplayName("Test validarCambioEstado(String, EstadoSolicitudEnum); when empty string; then throw BusinessException")
    void testValidarCambioEstado_whenEmptyString_thenThrowBusinessException() {
        // Arrange, Act and Assert
        assertThrows(BusinessException.class,
                () -> reglaNegocioSolicitud.validarCambioEstado("", EstadoSolicitudEnum.APROBADA));
    }

    /**
     * Test
     * {@link ReglaNegocioSolicitud#validarCambioEstado(String, EstadoSolicitudEnum)}.
     * <ul>
     *   <li>When {@code null}.</li>
     *   <li>Then throw {@link BusinessException}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link ReglaNegocioSolicitud#validarCambioEstado(String, EstadoSolicitudEnum)}
     */
    @Test
    @DisplayName("Test validarCambioEstado(String, EstadoSolicitudEnum); when 'null'; then throw BusinessException")
    void testValidarCambioEstado_whenNull_thenThrowBusinessException() {
        // Arrange, Act and Assert
        assertThrows(BusinessException.class,
                () -> reglaNegocioSolicitud.validarCambioEstado(null, EstadoSolicitudEnum.APROBADA));
    }
}
