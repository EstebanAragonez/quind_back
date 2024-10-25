package com.quind.prueba.application.services.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.quind.prueba.domain.model.TipoSolicitud;
import com.quind.prueba.domain.repository.TipoSolicitudRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TipoSolicitudServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TipoSolicitudServiceImplTest {
    @MockBean
    private TipoSolicitudRepository tipoSolicitudRepository;

    @Autowired
    private TipoSolicitudServiceImpl tipoSolicitudServiceImpl;

    /**
     * Test {@link TipoSolicitudServiceImpl#findAll()}.
     * <p>
     * Method under test: {@link TipoSolicitudServiceImpl#findAll()}
     */
    @Test
    @DisplayName("Test findAll()")
    void testFindAll() {
        // Arrange
        when(tipoSolicitudRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<TipoSolicitud> actualFindAllResult = tipoSolicitudServiceImpl.findAll();

        // Assert
        verify(tipoSolicitudRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
    }
}
