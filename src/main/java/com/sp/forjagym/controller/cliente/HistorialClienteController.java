package com.sp.forjagym.controller.cliente;

import com.sp.forjagym.controller.DatosDePrueba;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cliente/historial")
public class HistorialClienteController {

    @GetMapping
    public String historial(Model model) {
        model.addAttribute("compras", List.of(DatosDePrueba.compra()));
        return "cliente/historial";
    }

    @GetMapping("/{id}")
    public String comprobante(@PathVariable Long id, Model model) {
        model.addAttribute("compra", DatosDePrueba.compra());
        model.addAttribute("nueva", false);
        return "cliente/comprobante";
    }
}