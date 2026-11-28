package com.sp.forjagym.controller.cliente;

import com.sp.forjagym.dto.compra.PagoDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente/compra")
public class CompraClienteController {

    @GetMapping("/resumen")
    public String resumen() {
        return "cliente/compra_resumen";
    }

    @GetMapping("/pago")
    public String pago(Model model) {
        model.addAttribute("pago", new PagoDto());
        return "cliente/compra_pago";
    }

    @GetMapping("/confirmacion")
    public String confirmacion() {
        return "cliente/comprobante";
    }
}