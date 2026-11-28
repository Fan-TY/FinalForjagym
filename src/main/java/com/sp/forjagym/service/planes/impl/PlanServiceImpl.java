package com.sp.forjagym.service.planes.impl;

import com.sp.forjagym.Repository.admin.AdminRepository;
import com.sp.forjagym.entity.admin.AdminEntity;
import com.sp.forjagym.entity.planes.PlanEntity;
import com.sp.forjagym.mapper.planes.PlanMapper;
import com.sp.forjagym.model.planes.EstadoPlan;
import com.sp.forjagym.model.planes.Plan;
import com.sp.forjagym.Repository.planes.PlanRepository;
import com.sp.forjagym.service.planes.PlanService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PlanServiceImpl implements PlanService {
    private PlanRepository planRepository;
    private PlanMapper planMapper;
    private AdminRepository adminRepository;

    public PlanServiceImpl(PlanRepository planRepository, PlanMapper planMapper, AdminRepository adminRepository) {
        this.planRepository = planRepository;
        this.planMapper = planMapper;
        this.adminRepository = adminRepository;
    }

    @Override
    public Plan crear(Plan plan, Long idAdmin) {
        AdminEntity admin = adminRepository.findById(idAdmin).orElseThrow(() -> new IllegalArgumentException("Administrador no encontrado"));
        plan.setBeneficios(Plan.limpiarBeneficios(plan.getBeneficios()));
        validarCampos(plan);
        PlanEntity entity = planMapper.toEntity(plan, admin);
        entity.setId(null);
        if (entity.getEstadoPlan() == null) entity.setEstadoPlan(EstadoPlan.inactivo);
        return planMapper.toModel(planRepository.save(entity));
    }

    @Override
    public Plan buscarPorId(Long ID) {
        return planMapper.toModel(buscarEntidad(ID));
    }

    @Override
    public List<Plan> listarTodos() {
        return planMapper.toModelList(planRepository.findAll());
    }

    @Override
    public Plan actualizar(Long id, Plan plan) {
        PlanEntity existente = buscarEntidad(id);
        plan.setBeneficios(Plan.limpiarBeneficios(plan.getBeneficios()));
        validarCampos(plan);

        existente.setNombre(plan.getNombre());
        existente.setPrecio(plan.getPrecio());
        existente.setDuracionDias(plan.getDuracionDias());
        existente.setEstadoPlan(plan.getEstadoPlan());
        planMapper.reemplazarBeneficios(existente, plan.getBeneficios());

        return planMapper.toModel(planRepository.save(existente));
    }

    @Override
    public List<Plan> listarPlanesActivos() {
        return planMapper.toModelList(planRepository.findByEstadoPlan(EstadoPlan.activo));
    }

    private void validarCampos(Plan s) {
        if (s == null) {
            throw new IllegalArgumentException("La suscripción es obligatoria");
        }
        if (s.getNombre() == null || s.getNombre().isBlank()) {
            throw new IllegalArgumentException("La suscripción necesita un nombre");
        }
        if (s.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio de la suscripción tiene que ser mayor a 0");
        }
        if (s.getDuracionDias() <= 0 || s.getDuracionDias() > 12) {
            throw new IllegalArgumentException(
                    "La duración debe estar entre 1 y 12 meses");
        }
        if (s.getBeneficios() == null) {
            throw new IllegalArgumentException("La suscripción debe tener beneficios");
        }
    }

    private PlanEntity buscarEntidad(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La suscripción no existe"));
    }
}