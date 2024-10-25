package com.quind.prueba.application.ports.inbound;

import static org.mockito.Mockito.when;

import com.quind.prueba.application.services.DocumentoService;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {DocumentoController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class DocumentoControllerTest {
    @Autowired
    private DocumentoController documentoController;

    @MockBean
    private DocumentoService documentoService;

    /**
     * Test {@link DocumentoController#findAll()}.
     * <p>
     * Method under test: {@link DocumentoController#findAll()}
     */
    @Test
    @DisplayName("Test findAll()")
    void testFindAll() throws Exception {
        // Arrange
        when(documentoService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/documento");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(documentoController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
