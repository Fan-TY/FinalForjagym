package com.sp.forjagym.controller.cliente;

import com.sp.forjagym.controller.DatosDePrueba;
import com.sp.forjagym.controller.SesionClave;
import com.sp.forjagym.dto.planes.SeleccionPlanDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cliente/planes")
public class PlanClienteController {

    @GetMapping
    public String planes(Model model) {
        model.addAttribute("planes", List.of(DatosDePrueba.plan()));
        return "cliente/planes";
    }

    @PostMapping("/seleccionar")
    public String seleccionarPlan(@Valid SeleccionPlanDto seleccion, BindingResult errores,
                                  HttpSession session, Model model) {
        if (errores.hasErrors()) {
            model.addAttribute("planes", List.of(DatosDePrueba.plan()));
            model.addAttribute("error", "Selecciona un plan válido");
            return "cliente/planes";
        }
        session.setAttribute(SesionClave.PLAN_SELECCIONADO, seleccion.getIdPlan());
        return "redirect:/cliente/compra/resumen";
    }
}