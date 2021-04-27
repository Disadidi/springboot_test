package com.hdconsulting.test.springboot.app;

import com.hdconsulting.test.springboot.app.dao.CuentaRepository;
import com.hdconsulting.test.springboot.app.models.Cuenta;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@DataJpaTest
public class IntegrationJpaTest {

    @Autowired
    CuentaRepository cuentaRepository;

    @Test
    void testFindById() {
        Optional<Cuenta> cuenta = cuentaRepository.findById(1L);

        assertTrue(cuenta.isPresent());
        assertEquals("David", cuenta.orElseThrow().getPersona());

    }


    @Test
    void testFindByPersona() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("David");

        assertTrue(cuenta.isPresent());
        assertEquals("David", cuenta.orElseThrow().getPersona());
        assertEquals("1000.00", cuenta.orElseThrow().getSaldo().toPlainString());

    }


    @Test
    void testFindByPersonaThrowException() {
        Optional<Cuenta> cuenta = cuentaRepository.findByPersona("Hugo");

        assertThrows(NoSuchElementException.class, cuenta::orElseThrow);
        assertFalse(cuenta.isPresent());
    }

    @Test
    void testFindAll() {

        List<Cuenta> cuentas = cuentaRepository.findAll();
        assertFalse(cuentas.isEmpty());
        assertEquals(2, cuentas.size());
    }
}
