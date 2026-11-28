package com.sp.forjagym.mapper.cliente;

import com.sp.forjagym.entity.cliente.ClienteEntity;
import com.sp.forjagym.entity.cliente.TarjetaEntity;
import com.sp.forjagym.model.cliente.EstadoTarjeta;
import com.sp.forjagym.model.cliente.Tarjeta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Component
public class TarjetaMapper {

    public Tarjeta toModel(TarjetaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Tarjeta(
                entity.getId(),
                entity.getClienteEntity() == null ? null : entity.getClienteEntity().getId(),
                entity.getNumeroEnmascarado(),
                entity.getTitular(),
                entity.getSaldo(),
                entity.getEstado(),
                entity.getTipoTarjeta(),
                entity.getFechaExpiracion() == null ? null : YearMonth.from(entity.getFechaExpiracion())
        );
    }

    public List<Tarjeta> toModelList(List<TarjetaEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(this::toModel).toList();
    }

    public void vincular(TarjetaEntity entity, ClienteEntity cliente) {
        if (entity.getEstado() != EstadoTarjeta.disponible) {
            return;
        }
        entity.setClienteEntity(cliente);
        entity.setTitular(cliente.getNombre() + " " + cliente.getApellido());
        entity.setEstado(EstadoTarjeta.activo);
    }

    public void descontarSaldo(TarjetaEntity entity, BigDecimal monto) {
        entity.setSaldo(entity.getSaldo().subtract(monto));
    }

    public String ultimosDigitos(String numero) {
        String digitos = numero.replace(" ", "");
        return digitos.substring(digitos.length() - 4);
    }
}