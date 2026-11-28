package com.sp.forjagym.controller.admin;

import com.sp.forjagym.dto.planes.PlanDto;
import com.sp.forjagym.model.planes.EstadoPlan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/planes")
public class PlanAdminController {

    @GetMapping
    public String planes(@RequestParam(defaultValue = "planes") String vista, Model model) {
        model.addAttribute("vista", vista);
        return "admin/planes";
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        return formulario(model, "/admin/planes/crear", "CREAR PLAN");
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        return formulario(model, "/admin/planes/" + id + "/editar", "EDITAR PLAN");
    }

    private String formulario(Model model, String accion, String titulo) {
        model.addAttribute("plan", new PlanDto());
        model.addAttribute("accion", accion);
        model.addAttribute("titulo", titulo);
        model.addAttribute("estados", EstadoPlan.values());
        return "admin/plan_form";
    }
}