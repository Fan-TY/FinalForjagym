package com.sp.forjagym.Repository.planes;

import com.sp.forjagym.entity.planes.PlanEntity;
import com.sp.forjagym.model.planes.EstadoPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<PlanEntity, Long> {
    List<PlanEntity> findByEstadoPlan(EstadoPlan estado);
}
