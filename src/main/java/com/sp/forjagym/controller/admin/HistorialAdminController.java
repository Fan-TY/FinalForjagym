package com.sp.forjagym.controller.admin;

import com.sp.forjagym.controller.DatosDePrueba;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/historial")
public class HistorialAdminController {

    @GetMapping
    public String historial(Model model) {
        model.addAttribute("compras", List.of(DatosDePrueba.compra()));
        return "admin/historial";
    }
}