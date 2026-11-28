package com.sp.forjagym.controller.cliente;

import com.sp.forjagym.dto.cliente.PerfilClienteDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente/perfil")
public class PerfilClienteController {

    @GetMapping
    public String perfil(Model model) {
        model.addAttribute("perfil", new PerfilClienteDto());
        return "cliente/perfil";
    }
}