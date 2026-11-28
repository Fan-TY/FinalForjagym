package com.sp.forjagym.controller.cliente;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class LoginClienteController {

    @GetMapping("/login")
    public String login() {
        return "cliente/login";
    }
}