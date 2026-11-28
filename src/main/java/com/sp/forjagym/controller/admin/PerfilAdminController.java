package com.sp.forjagym.controller.admin;

import com.sp.forjagym.controller.DatosDePrueba;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/perfil")
public class PerfilAdminController {

    @GetMapping
    public String perfil(Model model) {
        model.addAttribute("admin", DatosDePrueba.admin());
        model.addAttribute("horarios", DatosDePrueba.horarios());
        return "admin/perfil";
    }
}