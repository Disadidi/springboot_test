package com.hdconsulting.test.springboot.app.services;

import com.hdconsulting.test.springboot.app.dao.BancoRepository;
import com.hdconsulting.test.springboot.app.dao.CuentaRepository;
import com.hdconsulting.test.springboot.app.models.Banco;
import com.hdconsulting.test.springboot.app.models.Cuenta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CuentaServiceImpl implements CuentaService {

    private CuentaRepository cuentaRepository;
    private BancoRepository bancoRepository;

    public CuentaServiceImpl(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cuenta> findAll() {
        return cuentaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findById(Long id) {
        return cuentaRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Cuenta save(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        cuentaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public int revisarTotalTransferencias(Long bancoId) {
        return bancoRepository.findById(bancoId).orElseThrow().getTotalTransferencias();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal revisarSaldo(Long cuentaId) {
        return cuentaRepository.findById(cuentaId).orElseThrow().getSaldo();
    }

    @Override
    @Transactional()
    public void tranferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto,
                          Long bancoId) {

        Cuenta cuentaOring = cuentaRepository.findById(numCuentaOrigen).orElseThrow();
        cuentaOring.debito(monto);
        cuentaRepository.save(cuentaOring);

        Cuenta cuentaDestino = cuentaRepository.findById(numCuentaDestino).orElseThrow();
        cuentaDestino.credito(monto);
        cuentaRepository.save(cuentaDestino);

        Banco banco = bancoRepository.findById(bancoId).orElseThrow();
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++totalTransferencias);
        bancoRepository.save(banco);
    }
}
