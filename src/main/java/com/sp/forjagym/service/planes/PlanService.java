package com.sp.forjagym.service.planes;

import com.sp.forjagym.model.planes.Plan;

import java.util.List;

public interface PlanService {
    Plan crear(Plan planes, Long idAdmin);
    Plan buscarPorId(Long ID);
    List<Plan> listarTodos();
    Plan actualizar(Long id, Plan suscripcion);
    List<Plan> listarPlanesActivos();
}
