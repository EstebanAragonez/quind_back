package com.quind.prueba.application.ports.inbound;

import static org.mockito.Mockito.when;

import com.quind.prueba.application.services.TipoSolicitudService;

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

@ContextConfiguration(classes = {TipoSolicitudController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TipoSolicitudControllerTest {
    @Autowired
    private TipoSolicitudController tipoSolicitudController;

    @MockBean
    private TipoSolicitudService tipoSolicitudService;

    /**
     * Test {@link TipoSolicitudController#findAll()}.
     * <p>
     * Method under test: {@link TipoSolicitudController#findAll()}
     */
    @Test
    @DisplayName("Test findAll()")
    void testFindAll() throws Exception {
        // Arrange
        when(tipoSolicitudService.findAll()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/tipo/solicitud");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(tipoSolicitudController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}
