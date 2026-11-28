package com.sp.forjagym.mapper.cliente;

import com.sp.forjagym.dto.compra.PagoDto;
import com.sp.forjagym.entity.cliente.ClienteEntity;
import com.sp.forjagym.entity.cliente.TarjetaEntity;
import com.sp.forjagym.model.cliente.EstadoTarjeta;
import com.sp.forjagym.model.cliente.Tarjeta;
import com.sp.forjagym.model.cliente.TipoTarjeta;
import org.springframework.stereotype.Component;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Component
public class TarjetaMapper {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("MM/yy");

    public Tarjeta toModel(TarjetaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Tarjeta(
                entity.getId(),
                entity.getNumeroEnmascarado(),
                entity.getEstado(),
                entity.getTipoTarjeta(),
                entity.getFechaExpiracion() == null ? null : YearMonth.from(entity.getFechaExpiracion())
        );
    }

    public TarjetaEntity toEntity(Tarjeta model, ClienteEntity clienteEntity) {
        if (model == null) {
            return null;
        }
        TarjetaEntity entity = new TarjetaEntity();
        entity.setId(model.getId());
        entity.setNumeroEnmascarado(model.getNumeroEnmascarado());
        entity.setEstado(model.getEstado());
        entity.setTipoTarjeta(model.getTipoTarjeta());
        entity.setFechaExpiracion(model.getFechaExpiracion() == null ? null : model.getFechaExpiracion().atEndOfMonth());
        entity.setClienteEntity(clienteEntity);
        return entity;
    }

    public TarjetaEntity toEntity(PagoDto dto, TipoTarjeta tipoTarjeta, ClienteEntity clienteEntity) {
        if (dto == null) {
            return null;
        }
        String digitos = dto.getNumeroTarjeta().replace(" ", "");
        TarjetaEntity entity = new TarjetaEntity();
        entity.setNumeroEnmascarado(digitos.substring(digitos.length() - 4));
        entity.setEstado(EstadoTarjeta.activo);
        entity.setTipoTarjeta(tipoTarjeta);
        entity.setFechaExpiracion(YearMonth.parse(dto.getVencimiento(), FORMATO).atEndOfMonth());
        entity.setClienteEntity(clienteEntity);
        return entity;
    }

    public List<Tarjeta> toModelList(List<TarjetaEntity> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(this::toModel)
                .toList();
    }
}