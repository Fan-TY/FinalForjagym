package com.sp.forjagym.controller.admin;

import com.sp.forjagym.controller.DatosDePrueba;
import com.sp.forjagym.dto.planes.PlanDto;
import com.sp.forjagym.model.planes.EstadoPlan;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/admin/planes")
public class PlanAdminController {

    @GetMapping
    public String planes(@RequestParam(defaultValue = "planes") String vista, Model model) {
        model.addAttribute("vista", vista);
        model.addAttribute("planes", List.of(DatosDePrueba.plan()));
        return "admin/planes";
    }

    @GetMapping("/crear")
    public String crearPlan(Model model) {
        model.addAttribute("plan", new PlanDto());
        return formularioPlan(model, "/admin/planes/crear", "CREAR PLAN");
    }

    @PostMapping("/crear")
    public String guardarPlan(@Valid @ModelAttribute("plan") PlanDto plan, BindingResult errores,
                              Model model) {
        if (errores.hasErrors()) {
            return formularioPlan(model, "/admin/planes/crear", "CREAR PLAN");
        }
        return "redirect:/admin/planes";
    }

    @GetMapping("/{id}/editar")
    public String editarPlan(@PathVariable Long id, Model model) {
        model.addAttribute("plan", new PlanDto());
        return formularioPlan(model, "/admin/planes/" + id + "/editar", "EDITAR PLAN");
    }

    @PostMapping("/{id}/editar")
    public String actualizarPlan(@PathVariable Long id,
                                 @Valid @ModelAttribute("plan") PlanDto plan, BindingResult errores,
                                 Model model) {
        if (errores.hasErrors()) {
            return formularioPlan(model, "/admin/planes/" + id + "/editar", "EDITAR PLAN");
        }
        return "redirect:/admin/planes";
    }

    @PostMapping("/categorias/crear")
    public String crearCategoria(@RequestParam(required = false) String nombre,
                                 @RequestParam(required = false) String descripcion,
                                 @RequestParam(required = false) Long idPlan) {
        return "redirect:/admin/planes?vista=categorias";
    }

    private String formularioPlan(Model model, String accion, String titulo) {
        model.addAttribute("accion", accion);
        model.addAttribute("titulo", titulo);
        model.addAttribute("estados", EstadoPlan.values());
        return "admin/plan_form";
    }
}