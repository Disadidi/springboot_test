package com.hdconsulting.test.springboot.app.dao;

import com.hdconsulting.test.springboot.app.models.Cuenta;

import java.util.List;

public interface CuentaDao {
    List<Cuenta> findAll();

    Cuenta findById(Long id);

    void update(Cuenta cuenta);
}
