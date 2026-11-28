package com.sp.forjagym.mapper.compra;

import com.sp.forjagym.entity.cliente.ClienteEntity;
import com.sp.forjagym.entity.compra.CompraEntity;
import com.sp.forjagym.mapper.cliente.ClienteMapper;
import com.sp.forjagym.model.compra.Compra;
import com.sp.forjagym.model.compra.DetalleCompra;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompraMapper {

    private final ClienteMapper clienteMapper;
    private final DetalleCompraMapper detalleCompraMapper;

    public CompraMapper(ClienteMapper clienteMapper, DetalleCompraMapper detalleCompraMapper) {
        this.clienteMapper = clienteMapper;
        this.detalleCompraMapper = detalleCompraMapper;
    }

    public Compra toModel(CompraEntity entity) {
        if (entity == null) {
            return null;
        }
        List<DetalleCompra> detalles = entity.getDetalles().stream()
                .map(detalleCompraMapper::toModel)
                .toList();
        return new Compra(
                entity.getId(),
                clienteMapper.toModel(entity.getClienteEntity()),
                detalles,
                entity.getFecha(),
                entity.getTotal(),
                entity.getEstado()
        );
    }

    public CompraEntity toEntity(Compra model, ClienteEntity clienteEntity) {
        if (model == null) {
            return null;
        }
        CompraEntity entity = new CompraEntity();
        entity.setId(model.getId());
        entity.setClienteEntity(clienteEntity);
        entity.setFecha(model.getFecha());
        entity.setTotal(model.getTotal());
        entity.setEstado(model.getEstado());
        if (model.getDetalles() != null) {
            model.getDetalles().forEach(d -> entity.getDetalles().add(detalleCompraMapper.toEntity(d, entity)));
        }
        return entity;
    }

    public List<Compra> toModelList(List<CompraEntity> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toModel).toList();
    }
}