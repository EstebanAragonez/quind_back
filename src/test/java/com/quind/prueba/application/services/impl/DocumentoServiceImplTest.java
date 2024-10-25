package com.quind.prueba.application.services.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.quind.prueba.domain.model.Documento;
import com.quind.prueba.domain.repository.DocumentoRepository;

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

@ContextConfiguration(classes = {DocumentoServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DocumentoServiceImplTest {
    @MockBean
    private DocumentoRepository documentoRepository;

    @Autowired
    private DocumentoServiceImpl documentoServiceImpl;

    /**
     * Test {@link DocumentoServiceImpl#findAll()}.
     * <p>
     * Method under test: {@link DocumentoServiceImpl#findAll()}
     */
    @Test
    @DisplayName("Test findAll()")
    void testFindAll() {
        // Arrange
        when(documentoRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Documento> actualFindAllResult = documentoServiceImpl.findAll();

        // Assert
        verify(documentoRepository).findAll();
        assertTrue(actualFindAllResult.isEmpty());
    }
}
