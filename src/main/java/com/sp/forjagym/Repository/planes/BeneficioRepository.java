package com.sp.forjagym.Repository.planes;

import com.sp.forjagym.entity.planes.BeneficioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BeneficioRepository extends JpaRepository<BeneficioEntity, Long> {
    List<BeneficioEntity> findByPlanEntity_Id(Long idPlan);
}