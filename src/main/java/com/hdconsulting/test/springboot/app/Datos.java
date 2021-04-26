package com.hdconsulting.test.springboot.app;

import com.hdconsulting.test.springboot.app.models.Banco;
import com.hdconsulting.test.springboot.app.models.Cuenta;

import java.math.BigDecimal;

public class Datos {
    public static final Cuenta CUENTA_001 = new Cuenta(1L, "David", new BigDecimal("1000"));
    public static final Cuenta CUENTA_002 = new Cuenta(2L, "Black", new BigDecimal("2000"));
    public static final Banco BANCO = new Banco(1L, "El banco financiero", 0);
}
