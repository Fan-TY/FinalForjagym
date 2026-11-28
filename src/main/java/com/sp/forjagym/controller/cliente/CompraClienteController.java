package com.sp.forjagym.controller.cliente;

import com.sp.forjagym.controller.DatosDePrueba;
import com.sp.forjagym.controller.SesionClave;
import com.sp.forjagym.dto.compra.PagoDto;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/cliente/compra")
public class CompraClienteController {

    @GetMapping("/resumen")
    public String resumen(HttpSession session, Model model) {
        Long idPlan = (Long) session.getAttribute(SesionClave.PLAN_SELECCIONADO);
        if (idPlan == null) {
            return "redirect:/cliente/planes";
        }
        model.addAttribute("plan", DatosDePrueba.plan());
        return "cliente/compra_resumen";
    }

    @GetMapping("/pago")
    public String pago(HttpSession session, Model model) {
        if (session.getAttribute(SesionClave.PLAN_SELECCIONADO) == null) {
            return "redirect:/cliente/planes";
        }
        model.addAttribute("pago", new PagoDto());
        model.addAttribute("plan", DatosDePrueba.plan());
        model.addAttribute("tarjetas", List.of());
        return "cliente/compra_pago";
    }

    @PostMapping("/pago")
    public String procesarPago(@Valid @ModelAttribute("pago") PagoDto pago, BindingResult errores,
                               HttpSession session, Model model) {
        if (session.getAttribute(SesionClave.PLAN_SELECCIONADO) == null) {
            return "redirect:/cliente/planes";
        }
        if (errores.hasErrors()) {
            model.addAttribute("plan", DatosDePrueba.plan());
            model.addAttribute("tarjetas", List.of());
            return "cliente/compra_pago";
        }
        session.removeAttribute(SesionClave.PLAN_SELECCIONADO);
        return "redirect:/cliente/compra/confirmacion";
    }

    @GetMapping("/confirmacion")
    public String confirmacion(Model model) {
        model.addAttribute("compra", DatosDePrueba.compra());
        model.addAttribute("nueva", true);
        return "cliente/comprobante";
    }
}