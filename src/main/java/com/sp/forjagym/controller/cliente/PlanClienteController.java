package com.sp.forjagym.controller.cliente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente/planes")
public class PlanClienteController {

    @GetMapping
    public String planes() {
        return "cliente/planes";
    }
}