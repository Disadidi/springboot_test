package com.hdconsulting.test.springboot.app.services;

import com.hdconsulting.test.springboot.app.models.Cuenta;

import java.math.BigDecimal;

public interface CuentaService {

    Cuenta findById(Long id);

    int revisarTotalTransferencias(Long bancoId);

    BigDecimal revisarSaldo(Long cuentaId);

    void tranferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId);
}
