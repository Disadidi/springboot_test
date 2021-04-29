package com.hdconsulting.test.springboot.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdconsulting.test.springboot.app.Datos;
import com.hdconsulting.test.springboot.app.models.Cuenta;
import com.hdconsulting.test.springboot.app.models.TransaccionDto;
import com.hdconsulting.test.springboot.app.services.CuentaService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(CuentaController.class)
class CuentaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CuentaService cuentaService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
    }

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

        verify(cuentaService).findById(1L);
    }

    @Test
    void testTransferir() throws Exception {

        //Given
        TransaccionDto dto = new TransaccionDto();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setMonto(new BigDecimal("100"));
        dto.setBancoId(1L);

        Map<String, Object> response = new HashMap<>();
        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Transferencia realizada con éxito!");
        response.put("transaccion", dto);

        // When
        mvc.perform(post("/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto)))
                //Then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.mensaje").value("Transferencia realizada con éxito!"))
                .andExpect(jsonPath("$.transaccion.cuentaOrigenId").value(dto.getCuentaOrigenId()))
        .andExpect(content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testListar() throws Exception {
        //Given
        List<Cuenta> cuentas = Arrays.asList(Datos.crearCuenta001().orElseThrow(),
                Datos.crearCuenta002().orElseThrow());
        when(cuentaService.findAll()).thenReturn(cuentas);
// when

        mvc.perform(get("/api/cuentas/").contentType(MediaType.APPLICATION_JSON_VALUE))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].persona").value("David"))
                .andExpect(jsonPath("$[1].persona").value("Black"))
                .andExpect(jsonPath("$[0].saldo").value("1000"))
                .andExpect(jsonPath("$[1].saldo").value("2000"))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(content().json(objectMapper.writeValueAsString(cuentas)));
    }

    @Test
    void testGuardar() throws Exception {
        Cuenta cuenta = new Cuenta(null, "Pepe", new BigDecimal("3000"));
        when(cuentaService.save(any(Cuenta.class))).then(invocation -> {
            Cuenta c = invocation.getArgument(0);
            c.setId(3L);
            return c;
                });

        mvc.perform(post("/api/cuentas/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(cuenta))
        ).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id", Matchers.is(3)))
        .andExpect(jsonPath("$.persona", Matchers.is("Pepe")))
        .andExpect(jsonPath("$.saldo", Matchers.is(3000)));

        verify(cuentaService).save(any());


    }
}
