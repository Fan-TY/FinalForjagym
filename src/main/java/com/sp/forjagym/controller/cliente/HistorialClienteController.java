package com.sp.forjagym.controller.cliente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente/historial")
public class HistorialClienteController {

    @GetMapping
    public String historial() {
        return "cliente/historial";
    }

    @GetMapping("/{id}")
    public String comprobante(@PathVariable Long id) {
        return "cliente/comprobante";
    }
}