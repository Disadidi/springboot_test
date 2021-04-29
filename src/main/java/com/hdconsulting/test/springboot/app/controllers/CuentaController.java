package com.hdconsulting.test.springboot.app.controllers;

import com.hdconsulting.test.springboot.app.models.Cuenta;
import com.hdconsulting.test.springboot.app.models.TransaccionDto;
import com.hdconsulting.test.springboot.app.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cuenta detalle(@PathVariable Long id) {
        return cuentaService.findById(id);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> tranferir(@RequestBody TransaccionDto dto) {
        cuentaService.tranferir(dto.getCuentaOrigenId(),
                dto.getCuentaDestinoId(), dto.getMonto(),
                dto.getBancoId());
        Map<String, Object> response = new HashMap<>();

        response.put("date", LocalDate.now().toString());
        response.put("status", "OK");
        response.put("mensaje", "Transferencia realizada con éxito!");
        response.put("transaction", dto);

        return ResponseEntity.ok(response);

    }
}
