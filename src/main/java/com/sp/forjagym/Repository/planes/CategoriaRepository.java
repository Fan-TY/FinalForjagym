package com.sp.forjagym.Repository.planes;

import com.sp.forjagym.entity.planes.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long> {
    List<CategoriaEntity> findByPlanEntity_Id(Long idPlan);
    boolean existsByNombre(String nombre);
}