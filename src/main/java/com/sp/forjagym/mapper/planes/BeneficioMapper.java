package com.sp.forjagym.mapper.planes;

import com.sp.forjagym.entity.planes.BeneficioEntity;
import com.sp.forjagym.entity.planes.PlanEntity;
import com.sp.forjagym.model.planes.Beneficio;
import org.springframework.stereotype.Component;

@Component
public class BeneficioMapper {

    public Beneficio toModel(BeneficioEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Beneficio(entity.getId(), entity.getDescripcion());
    }

    public BeneficioEntity toEntity(Beneficio model, PlanEntity planEntity) {
        if (model == null) {
            return null;
        }
        BeneficioEntity entity = new BeneficioEntity();
        entity.setId(model.getId());
        entity.setDescripcion(model.getDescripcion());
        entity.setPlanEntity(planEntity);
        return entity;
    }
}