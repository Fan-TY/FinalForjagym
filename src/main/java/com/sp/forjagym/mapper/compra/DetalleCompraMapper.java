package com.sp.forjagym.mapper.compra;

import com.sp.forjagym.entity.compra.CompraEntity;
import com.sp.forjagym.entity.compra.DetalleCompraEntity;
import com.sp.forjagym.entity.planes.PlanEntity;
import com.sp.forjagym.mapper.planes.PlanMapper;
import com.sp.forjagym.model.compra.DetalleCompra;
import org.springframework.stereotype.Component;

@Component
public class DetalleCompraMapper {

    private final PlanMapper planMapper;

    public DetalleCompraMapper(PlanMapper planMapper) {
        this.planMapper = planMapper;
    }

    public DetalleCompra toModel(DetalleCompraEntity entity) {
        if (entity == null) {
            return null;
        }
        return new DetalleCompra(
                entity.getId(),
                planMapper.toModel(entity.getPlanEntity()),
                entity.getPrecioUnitario(),
                entity.getCantidad(),
                entity.getSubtotal()
        );
    }

    public DetalleCompraEntity toEntity(DetalleCompra model, CompraEntity compraEntity) {
        if (model == null) {
            return null;
        }
        DetalleCompraEntity entity = new DetalleCompraEntity();
        entity.setId(model.getId());
        entity.setCompra(compraEntity);
        if (model.getPlan() != null) {
            PlanEntity planReferencia = new PlanEntity();
            planReferencia.setId(model.getPlan().getId());
            entity.setPlanEntity(planReferencia);
        }
        entity.setPrecioUnitario(model.getPrecioUnitario());
        entity.setCantidad(model.getCantidad());
        entity.setSubtotal(model.getSubtotal());
        return entity;
    }
}