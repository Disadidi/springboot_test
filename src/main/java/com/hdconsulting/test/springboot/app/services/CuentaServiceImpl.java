package com.hdconsulting.test.springboot.app.services;

import com.hdconsulting.test.springboot.app.dao.BancoDao;
import com.hdconsulting.test.springboot.app.dao.CuentaDao;
import com.hdconsulting.test.springboot.app.models.Banco;
import com.hdconsulting.test.springboot.app.models.Cuenta;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CuentaServiceImpl implements CuentaService {

    private CuentaDao cuentaDao;
    private BancoDao bancoDao;

    public CuentaServiceImpl(CuentaDao cuentaDao, BancoDao bancoDao) {
        this.cuentaDao = cuentaDao;
        this.bancoDao = bancoDao;
    }

    @Override
    public Cuenta findById(Long id) {
        return cuentaDao.findById(id);
    }

    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        return bancoDao.findById(bancoId).getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        return cuentaDao.findById(cuentaId).getSaldo();
    }

    @Override
    public void tranferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto,
                          Long bancoId) {

        Cuenta cuentaOring = cuentaDao.findById(numCuentaOrigen);
        cuentaOring.debito(monto);
        cuentaDao.update(cuentaOring);

        Cuenta cuentaDestino = cuentaDao.findById(numCuentaDestino);
        cuentaDestino.credito(monto);
        cuentaDao.update(cuentaDestino);

        Banco banco = bancoDao.findById(bancoId);
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        bancoDao.update(banco);
    }
}
