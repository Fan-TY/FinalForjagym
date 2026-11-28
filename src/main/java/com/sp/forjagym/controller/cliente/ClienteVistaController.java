package com.sp.forjagym.controller.cliente;

import com.sp.forjagym.controller.DatosDePrueba;
import com.sp.forjagym.dto.cliente.PerfilClienteDto;
import com.sp.forjagym.dto.compra.PagoDto;
import com.sp.forjagym.dto.planes.SeleccionPlanDto;
import com.sp.forjagym.model.cliente.Cliente;
import jakarta.servlet.http.HttpSession;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteVistaController {

    private static final String SES_PLAN = "idPlanSeleccionado";

    @GetMapping("/inicio")
    public String inicio() {
        return "cliente/inicio";
    }

    @GetMapping("/contacto")
    public String contacto() {
        return "cliente/contacto";
    }

    @GetMapping("/login")
    public String login() {
        return "cliente/login";
    }

    @GetMapping("/planes")
    public String planes(Model model) {
        model.addAttribute("planes", List.of(DatosDePrueba.plan()));
        return "cliente/planes";
    }

    @GetMapping("/compra/resumen")
    public String resumen(HttpSession session, Model model) {
        Long idPlan = (Long) session.getAttribute(SES_PLAN);
        if (idPlan == null) {
            return "redirect:/cliente/planes";
        }
        model.addAttribute("plan", DatosDePrueba.plan());
        return "cliente/compra_resumen";
    }

    @GetMapping("/compra/pago")
    public String pago(HttpSession session, Model model) {
        if (session.getAttribute(SES_PLAN) == null) {
            return "redirect:/cliente/planes";
        }
        model.addAttribute("pago", new PagoDto());
        model.addAttribute("plan", DatosDePrueba.plan());
        model.addAttribute("tarjetas", List.of());
        return "cliente/compra_pago";
    }

    @GetMapping("/compra/confirmacion")
    public String confirmacion(Model model) {
        model.addAttribute("compra", DatosDePrueba.compra());
        model.addAttribute("nueva", true);
        return "cliente/comprobante";
    }

    @GetMapping("/historial")
    public String historial(Model model) {
        model.addAttribute("compras", List.of(DatosDePrueba.compra()));
        return "cliente/historial";
    }

    @GetMapping("/historial/{id}")
    public String comprobante(@PathVariable Long id, Model model) {
        model.addAttribute("compra", DatosDePrueba.compra());
        model.addAttribute("nueva", false);
        return "cliente/comprobante";
    }

    @GetMapping("/perfil")
    public String perfil(Model model) {
        Cliente cliente = DatosDePrueba.cliente();
        PerfilClienteDto perfil = new PerfilClienteDto();
        perfil.setNombre(cliente.getNombre());
        perfil.setApellido(cliente.getApellido());
        perfil.setCorreo(cliente.getCorreo());
        perfil.setTelefono(cliente.getTelefono());
        perfil.setContactoEmergencia(cliente.getContactoEmergencia());
        return vistaPerfil(model, perfil);
    }

    @PostMapping("/planes/seleccionar")
    public String seleccionarPlan(@Valid SeleccionPlanDto seleccion, BindingResult errores,
                                  HttpSession session, Model model) {
        if (errores.hasErrors()) {
            model.addAttribute("planes", List.of(DatosDePrueba.plan()));
            model.addAttribute("error", "Selecciona un plan válido");
            return "cliente/planes";
        }
        session.setAttribute(SES_PLAN, seleccion.getIdPlan());
        return "redirect:/cliente/compra/resumen";
    }

    @PostMapping("/compra/pago")
    public String procesarPago(@Valid @ModelAttribute("pago") PagoDto pago, BindingResult errores,
                               HttpSession session, Model model) {
        if (session.getAttribute(SES_PLAN) == null) {
            return "redirect:/cliente/planes";
        }
        if (errores.hasErrors()) {
            model.addAttribute("plan", DatosDePrueba.plan());
            model.addAttribute("tarjetas", List.of());
            return "cliente/compra_pago";
        }
        session.removeAttribute(SES_PLAN);
        return "redirect:/cliente/compra/confirmacion";
    }

    @PostMapping("/perfil/actualizar")
    public String actualizarPerfil(@Valid @ModelAttribute("perfil") PerfilClienteDto perfil,
                                   BindingResult errores, RedirectAttributes redirect, Model model) {
        if (errores.hasErrors()) {
            return vistaPerfil(model, perfil);
        }
        redirect.addFlashAttribute("exito", "Datos actualizados correctamente");
        return "redirect:/cliente/perfil";
    }

    @PostMapping("/perfil/cancelar-plan")
    public String cancelarPlan(@RequestParam Long idDetalle, RedirectAttributes redirect) {
        redirect.addFlashAttribute("exito", "Tu plan fue cancelado");
        return "redirect:/cliente/perfil";
    }

    @PostMapping("/perfil/tarjetas/eliminar")
    public String eliminarTarjeta(@RequestParam Long idTarjeta, RedirectAttributes redirect) {
        redirect.addFlashAttribute("exito", "Tarjeta eliminada");
        return "redirect:/cliente/perfil";
    }


    private String vistaPerfil(Model model, PerfilClienteDto perfil) {
        Cliente cliente = DatosDePrueba.cliente();
        model.addAttribute("cliente", cliente);
        model.addAttribute("perfil", perfil);
        model.addAttribute("tarjetas", cliente.getTarjetas());
        model.addAttribute("suscripcionActiva", DatosDePrueba.detalle());
        return "cliente/perfil";
    }
}