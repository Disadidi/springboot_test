package com.hdconsulting.test.springboot.app.controllers;

import com.hdconsulting.test.springboot.app.Datos;
import com.hdconsulting.test.springboot.app.services.CuentaService;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CuentaController.class)
class CuentaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CuentaService cuentaService;

    @Test
    void testDetalle() throws Exception {
        //Given
        when(cuentaService.findById(1L)).thenReturn(Datos.crearCuenta001().orElseThrow());

        //When
        mvc.perform(get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON_VALUE))
                //then
        .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.persona").value("David"))
                .andExpect(jsonPath("$.saldo").value("1000"));
    }
}