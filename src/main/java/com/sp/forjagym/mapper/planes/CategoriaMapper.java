package com.sp.forjagym.mapper.planes;

import com.sp.forjagym.entity.planes.CategoriaEntity;
import com.sp.forjagym.entity.planes.PlanEntity;
import com.sp.forjagym.model.planes.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toModel(CategoriaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Categoria(entity.getId(), entity.getNombre(), entity.getDescripcion());
    }

    public CategoriaEntity toEntity(Categoria model, PlanEntity planEntity) {
        if (model == null) {
            return null;
        }
        CategoriaEntity entity = new CategoriaEntity();
        entity.setId(model.getId());
        entity.setNombre(model.getNombre());
        entity.setDescripcion(model.getDescripcion());
        entity.setPlanEntity(planEntity);
        return entity;
    }
}