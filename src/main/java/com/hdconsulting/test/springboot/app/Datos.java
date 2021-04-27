package com.hdconsulting.test.springboot.app;

import com.hdconsulting.test.springboot.app.models.Banco;
import com.hdconsulting.test.springboot.app.models.Cuenta;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.OptionalInt;

public class Datos {


    public static Optional<Cuenta> crearCuenta001(){
        return Optional.of(new Cuenta(1L, "David", new BigDecimal("1000")));
    }

    public static Optional<Cuenta> crearCuenta002() {
        return Optional.of(new Cuenta(2L, "Black", new BigDecimal("2000")));
    }

    public static Optional<Banco> crearBanco() {
        return Optional.of(new Banco(1L, "El banco financiero", 0));
    }
}
