package com.hdconsulting.test.springboot.app;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.hdconsulting.test.springboot.app.dao.BancoDao;
import com.hdconsulting.test.springboot.app.dao.CuentaDao;
import com.hdconsulting.test.springboot.app.exceptions.DineroInsuficienteException;
import com.hdconsulting.test.springboot.app.models.Banco;
import com.hdconsulting.test.springboot.app.models.Cuenta;
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
		Datos.CUENTA_001.setSaldo(new BigDecimal("1000"));
		Datos.CUENTA_002.setSaldo(new BigDecimal("2000"));
		Datos.BANCO.setTotalTransferencias(0);
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

		int total = service.revisarTotalTransferencias(1L);
		assertEquals(1, total);

		//cuentaRepository.findById est appelé 3 fois par chaque argument (1L et 2L)
		verify(cuentaRepository, times(3)).findById(1L);
		verify(cuentaRepository, times(3)).findById(2L);
		verify(cuentaRepository, times(2)).update(any(Cuenta.class));

		verify(bancoRepository, times(2)).findById(1L);
		verify(bancoRepository).update(any(Banco.class));

		verify(cuentaRepository, times(6)).findById(anyLong());
		verify(cuentaRepository, never()).findAll();
	}

	@Test
	void contextLoads2() {
		when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);
		when(cuentaRepository.findById(2L)).thenReturn(Datos.CUENTA_002);
		when(bancoRepository.findById(1L)).thenReturn(Datos.BANCO);

		BigDecimal saldoOrigen = service.revisarSaldo(1L);
		BigDecimal saldoDestino = service.revisarSaldo(2L);

		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		assertThrows(DineroInsuficienteException.class, () -> {
			service.tranferir(1L, 2L, new BigDecimal("1200"), 1L);
		});

		saldoOrigen = service.revisarSaldo(1L);
		saldoDestino = service.revisarSaldo(2L);

		//Pas de modification
		assertEquals("1000", saldoOrigen.toPlainString());
		assertEquals("2000", saldoDestino.toPlainString());

		int total = service.revisarTotalTransferencias(1L);
		assertEquals(0, total);

		//cuentaRepository.findById est appelé 3 fois par chaque argument (1L et 2L)
		verify(cuentaRepository, times(3)).findById(1L);
		verify(cuentaRepository, times(2)).findById(2L);
		verify(cuentaRepository, never()).update(any(Cuenta.class));

		verify(bancoRepository, times(1)).findById(1L);
		verify(bancoRepository, never()).update(any(Banco.class));
		verify(cuentaRepository, times(5)).findById(anyLong());
		verify(cuentaRepository, never()).findAll();
	}

	@Test
	void contextLoads3() {
		when(cuentaRepository.findById(1L)).thenReturn(Datos.CUENTA_001);

		Cuenta cuenta1 = cuentaRepository.findById(1L);
		Cuenta cuenta2 = cuentaRepository.findById(1L);

		assertSame(cuenta1, cuenta2);

		verify(cuentaRepository, times(2)).findById(1L);
	}
}
