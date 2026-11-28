package com.sp.forjagym.mapper.compra;

import com.sp.forjagym.entity.cliente.ClienteEntity;
import com.sp.forjagym.entity.cliente.TarjetaEntity;
import com.sp.forjagym.entity.compra.CompraEntity;
import com.sp.forjagym.mapper.cliente.ClienteMapper;
import com.sp.forjagym.mapper.cliente.TarjetaMapper;
import com.sp.forjagym.model.compra.Compra;
import com.sp.forjagym.model.compra.DetalleCompra;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompraMapper {

    private final ClienteMapper clienteMapper;
    private final TarjetaMapper tarjetaMapper;
    private final DetalleCompraMapper detalleCompraMapper;

    public CompraMapper(ClienteMapper clienteMapper, TarjetaMapper tarjetaMapper, DetalleCompraMapper detalleCompraMapper) {
        this.clienteMapper = clienteMapper;
        this.tarjetaMapper = tarjetaMapper;
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
                tarjetaMapper.toModel(entity.getTarjetaEntity()),
                detalles,
                entity.getFecha(),
                entity.getTotal(),
                entity.getEstado()
        );
    }

    public CompraEntity toEntity(Compra model, ClienteEntity clienteEntity, TarjetaEntity tarjetaEntity) {
        if (model == null) {
            return null;
        }
        CompraEntity entity = new CompraEntity();
        entity.setId(model.getId());
        entity.setClienteEntity(clienteEntity);
        entity.setTarjetaEntity(tarjetaEntity);
        entity.setFecha(model.getFecha());
        entity.setTotal(model.getTotal());
        entity.setEstado(model.getEstado());
        if (model.getDetalles() != null) {
            model.getDetalles().forEach(d -> entity.getDetalles().add(detalleCompraMapper.toEntity(d, entity)));
        }
        return entity;
    }
}