package com.sp.forjagym.mapper.compra;

import com.sp.forjagym.entity.compra.DetalleCompraEntity;
import com.sp.forjagym.entity.compra.SuscripcionClienteEntity;
import com.sp.forjagym.model.compra.SuscripcionCliente;
import org.springframework.stereotype.Component;

@Component
public class SuscripcionClienteMapper {

    private final DetalleCompraMapper detalleCompraMapper;

    public SuscripcionClienteMapper(DetalleCompraMapper detalleCompraMapper) {
        this.detalleCompraMapper = detalleCompraMapper;
    }

    public SuscripcionCliente toModel(SuscripcionClienteEntity entity) {
        if (entity == null) {
            return null;
        }
        return new SuscripcionCliente(
                entity.getId(),
                detalleCompraMapper.toModel(entity.getDetalleCompra()),
                entity.getFechaInicio(),
                entity.getFechaFin(),
                entity.getEstado()
        );
    }

    public SuscripcionClienteEntity toEntity(SuscripcionCliente model, DetalleCompraEntity detalleCompraEntity) {
        if (model == null) {
            return null;
        }
        SuscripcionClienteEntity entity = new SuscripcionClienteEntity();
        entity.setId(model.getId());
        entity.setDetalleCompra(detalleCompraEntity);
        entity.setFechaInicio(model.getFechaInicio());
        entity.setFechaFin(model.getFechaFin());
        entity.setEstado(model.getEstado());
        return entity;
    }
}