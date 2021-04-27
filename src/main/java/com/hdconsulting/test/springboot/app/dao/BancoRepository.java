package com.hdconsulting.test.springboot.app.dao;

import com.hdconsulting.test.springboot.app.models.Banco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BancoRepository extends JpaRepository<Banco, Long> {

}
