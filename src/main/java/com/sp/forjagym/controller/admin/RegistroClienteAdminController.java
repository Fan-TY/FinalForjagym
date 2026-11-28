package com.sp.forjagym.controller.admin;

import com.sp.forjagym.dto.cliente.RegistroClienteDto;
import com.sp.forjagym.dto.compra.PagoDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/clientes/registrar")
public class RegistroClienteAdminController {

    @GetMapping
    public String datos(Model model) {
        model.addAttribute("registro", new RegistroClienteDto());
        return "admin/registro_datos";
    }

    @GetMapping("/plan")
    public String plan() {
        return "admin/registro_plan";
    }

    @GetMapping("/pago")
    public String pago(Model model) {
        model.addAttribute("pago", new PagoDto());
        return "admin/registro_pago";
    }

    @GetMapping("/exito")
    public String exito() {
        return "admin/registro_exito";
    }

    @GetMapping("/cancelar")
    public String cancelar() {
        return "redirect:/admin/clientes/registrar";
    }
}