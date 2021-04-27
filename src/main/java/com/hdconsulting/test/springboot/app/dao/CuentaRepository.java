package com.hdconsulting.test.springboot.app.dao;

import com.hdconsulting.test.springboot.app.models.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    Optional<Cuenta> findByPersona(String persona);
}
