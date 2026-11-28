package com.sp.forjagym.controller.admin;

import com.sp.forjagym.controller.DatosDePrueba;
import com.sp.forjagym.dto.cliente.RegistroClienteDto;
import com.sp.forjagym.dto.compra.PagoDto;
import com.sp.forjagym.dto.planes.PlanDto;
import com.sp.forjagym.dto.planes.SeleccionPlanDto;
import com.sp.forjagym.model.planes.EstadoPlan;
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

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminVistaController {

    private static final String SES_REGISTRO = "registroCliente";
    private static final String SES_PLAN_REGISTRO = "idPlanRegistro";


    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("/perfil")
    public String perfil(Model model) {
        model.addAttribute("admin", DatosDePrueba.admin());
        model.addAttribute("horarios", DatosDePrueba.horarios());
        return "admin/perfil";
    }

    @GetMapping("/planes")
    public String planes(@RequestParam(defaultValue = "planes") String vista, Model model) {
        model.addAttribute("vista", vista);
        model.addAttribute("planes", List.of(DatosDePrueba.plan()));
        return "admin/planes";
    }

    @GetMapping("/planes/crear")
    public String crearPlan(Model model) {
        model.addAttribute("plan", new PlanDto());
        return formularioPlan(model, "/admin/planes/crear", "CREAR PLAN");
    }

    @GetMapping("/planes/{id}/editar")
    public String editarPlan(@PathVariable Long id, Model model) {
        model.addAttribute("plan", new PlanDto());
        return formularioPlan(model, "/admin/planes/" + id + "/editar", "EDITAR PLAN");
    }

    @GetMapping("/clientes/registrar")
    public String registroDatos(HttpSession session, Model model) {
        Object guardado = session.getAttribute(SES_REGISTRO);
        model.addAttribute("registro", guardado != null ? guardado : new RegistroClienteDto());
        return "admin/registro_datos";
    }

    @GetMapping("/clientes/registrar/plan")
    public String registroPlan(HttpSession session, Model model) {
        if (session.getAttribute(SES_REGISTRO) == null) {
            return "redirect:/admin/clientes/registrar";
        }
        model.addAttribute("planes", List.of(DatosDePrueba.plan()));
        model.addAttribute("seleccionado", session.getAttribute(SES_PLAN_REGISTRO));
        return "admin/registro_plan";
    }

    @GetMapping("/clientes/registrar/pago")
    public String registroPago(HttpSession session, Model model) {
        if (session.getAttribute(SES_REGISTRO) == null) {
            return "redirect:/admin/clientes/registrar";
        }
        if (session.getAttribute(SES_PLAN_REGISTRO) == null) {
            return "redirect:/admin/clientes/registrar/plan";
        }
        model.addAttribute("pago", new PagoDto());
        model.addAttribute("plan", DatosDePrueba.plan());
        return "admin/registro_pago";
    }

    @GetMapping("/clientes/registrar/exito")
    public String registroExito(Model model) {
        model.addAttribute("cliente", DatosDePrueba.cliente());
        model.addAttribute("compra", DatosDePrueba.compra());
        return "admin/registro_exito";
    }

    @GetMapping("/clientes/registrar/cancelar")
    public String registroCancelar(HttpSession session) {
        session.removeAttribute(SES_REGISTRO);
        session.removeAttribute(SES_PLAN_REGISTRO);
        return "redirect:/admin/clientes/registrar";
    }

    @GetMapping("/historial")
    public String historial(Model model) {
        model.addAttribute("compras", List.of(DatosDePrueba.compra()));
        return "admin/historial";
    }

    @PostMapping("/planes/crear")
    public String guardarPlan(@Valid @ModelAttribute("plan") PlanDto plan, BindingResult errores,
                              Model model) {
        if (errores.hasErrors()) {
            return formularioPlan(model, "/admin/planes/crear", "CREAR PLAN");
        }
        return "redirect:/admin/planes";
    }

    @PostMapping("/planes/{id}/editar")
    public String actualizarPlan(@PathVariable Long id,
                                 @Valid @ModelAttribute("plan") PlanDto plan, BindingResult errores,
                                 Model model) {
        if (errores.hasErrors()) {
            return formularioPlan(model, "/admin/planes/" + id + "/editar", "EDITAR PLAN");
        }
        return "redirect:/admin/planes";
    }

    @PostMapping("/planes/categorias/crear")
    public String crearCategoria(@RequestParam(required = false) String nombre,
                                 @RequestParam(required = false) String descripcion,
                                 @RequestParam(required = false) Long idPlan) {
        return "redirect:/admin/planes?vista=categorias";
    }

    @PostMapping("/clientes/registrar/datos")
    public String guardarDatos(@Valid @ModelAttribute("registro") RegistroClienteDto registro,
                               BindingResult errores, HttpSession session) {
        if (errores.hasErrors()) {
            return "admin/registro_datos";
        }
        registro.normalizar();
        session.setAttribute(SES_REGISTRO, registro);
        return "redirect:/admin/clientes/registrar/plan";
    }

    @PostMapping("/clientes/registrar/plan")
    public String guardarPlanRegistro(@Valid SeleccionPlanDto seleccion, BindingResult errores,
                                      HttpSession session, Model model) {
        if (session.getAttribute(SES_REGISTRO) == null) {
            return "redirect:/admin/clientes/registrar";
        }
        if (errores.hasErrors()) {
            model.addAttribute("planes", List.of(DatosDePrueba.plan()));
            model.addAttribute("error", "Selecciona un plan");
            return "admin/registro_plan";
        }
        session.setAttribute(SES_PLAN_REGISTRO, seleccion.getIdPlan());
        return "redirect:/admin/clientes/registrar/pago";
    }

    @PostMapping("/clientes/registrar/pago")
    public String guardarPago(@Valid @ModelAttribute("pago") PagoDto pago, BindingResult errores,
                              HttpSession session, Model model) {
        if (session.getAttribute(SES_REGISTRO) == null) {
            return "redirect:/admin/clientes/registrar";
        }
        if (errores.hasErrors()) {
            model.addAttribute("plan", DatosDePrueba.plan());
            return "admin/registro_pago";
        }
        session.removeAttribute(SES_REGISTRO);
        session.removeAttribute(SES_PLAN_REGISTRO);
        return "redirect:/admin/clientes/registrar/exito";
    }

    private String formularioPlan(Model model, String accion, String titulo) {
        model.addAttribute("accion", accion);
        model.addAttribute("titulo", titulo);
        model.addAttribute("estados", EstadoPlan.values());
        return "admin/plan_form";
    }
}