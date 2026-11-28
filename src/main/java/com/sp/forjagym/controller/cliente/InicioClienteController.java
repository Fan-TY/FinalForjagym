package com.sp.forjagym.controller.cliente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class InicioClienteController {

    @GetMapping("/inicio")
    public String inicio() {
        return "cliente/inicio";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "cliente/contacto";
    }
}