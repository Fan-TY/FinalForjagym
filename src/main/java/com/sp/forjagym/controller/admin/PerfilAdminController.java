package com.sp.forjagym.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/perfil")
public class PerfilAdminController {

    @GetMapping
    public String perfil() {
        return "admin/perfil";
    }
}