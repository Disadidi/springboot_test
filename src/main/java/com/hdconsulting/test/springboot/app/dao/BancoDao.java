package com.hdconsulting.test.springboot.app.dao;

import com.hdconsulting.test.springboot.app.models.Banco;

import java.util.List;

public interface BancoDao {

    List<Banco> findAll();

    Banco findById(Long id);

    void update(Banco banco);
}
