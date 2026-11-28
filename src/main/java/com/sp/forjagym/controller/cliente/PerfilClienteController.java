package com.sp.forjagym.controller.cliente;

import com.sp.forjagym.controller.DatosDePrueba;
import com.sp.forjagym.dto.cliente.PerfilClienteDto;
import com.sp.forjagym.model.cliente.Cliente;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente/perfil")
public class PerfilClienteController {

    @GetMapping
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

    @PostMapping("/actualizar")
    public String actualizarPerfil(@Valid @ModelAttribute("perfil") PerfilClienteDto perfil,
                                   BindingResult errores, RedirectAttributes redirect, Model model) {
        if (errores.hasErrors()) {
            return vistaPerfil(model, perfil);
        }
        redirect.addFlashAttribute("exito", "Datos actualizados correctamente");
        return "redirect:/cliente/perfil";
    }

    @PostMapping("/cancelar-plan")
    public String cancelarPlan(@RequestParam Long idDetalle, RedirectAttributes redirect) {
        redirect.addFlashAttribute("exito", "Tu plan fue cancelado");
        return "redirect:/cliente/perfil";
    }

    @PostMapping("/tarjetas/eliminar")
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
