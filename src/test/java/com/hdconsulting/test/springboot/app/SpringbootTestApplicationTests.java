package com.hdconsulting.test.springboot.app;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hdconsulting.test.springboot.app.dao.BancoDao;
import com.hdconsulting.test.springboot.app.dao.CuentaDao;
import com.hdconsulting.test.springboot.app.services.CuentaService;
import com.hdconsulting.test.springboot.app.services.CuentaServiceImpl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class SpringbootTestApplicationTests {

	CuentaDao cuentaRepository;
	BancoDao bancoRepository;

	CuentaService service;

	@BeforeEach
	 void beforeEach() {
		cuentaRepository = mock(CuentaDao.class);
		bancoRepository = mock(BancoDao.class);
		service = new CuentaServiceImpl(cuentaRepository, bancoRepository);

	}

	@Test
	void contextLoads() {
		when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
		when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
		when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);

		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);

		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		service.tranferir(1L, 2L, new BigDecimal("100"), 1L);

		saldoOrigen = service.revisarSaldo(1L);
		saldoDestino = service.revisarSaldo(2L);

		assertEquals("900", saldoOrigen.toPlainString());
		assertEquals("2100", saldoDestino.toPlainString());


	}

}
