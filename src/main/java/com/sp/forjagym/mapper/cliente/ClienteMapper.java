package com.sp.forjagym.mapper.cliente;

import com.sp.forjagym.dto.cliente.PerfilClienteDto;
import com.sp.forjagym.dto.cliente.RegistroClienteDto;
import com.sp.forjagym.entity.cliente.ClienteEntity;
import com.sp.forjagym.model.cliente.Cliente;
import com.sp.forjagym.model.cliente.Tarjeta;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteMapper {

    private final TarjetaMapper tarjetaMapper;

    public ClienteMapper(TarjetaMapper tarjetaMapper) {
        this.tarjetaMapper = tarjetaMapper;
    }

    public Cliente toModel(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }
        List<Tarjeta> tarjetas = entity.getTarjetas().stream()
                .map(tarjetaMapper::toModel)
                .toList();
        return new Cliente(
                entity.getId(),
                entity.getCorreo(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getDni(),
                entity.getTelefono(),
                entity.getFechaNacimiento(),
                entity.getContactoEmergencia(),
                tarjetas
        );
    }

    public ClienteEntity toEntity(Cliente model) {
        if (model == null) {
            return null;
        }
        ClienteEntity entity = new ClienteEntity();
        entity.setId(model.getId());
        entity.setCorreo(model.getCorreo());
        entity.setNombre(model.getNombre());
        entity.setApellido(model.getApellido());
        entity.setDni(model.getDni());
        entity.setTelefono(model.getTelefono());
        entity.setFechaNacimiento(model.getFechaNacimiento());
        entity.setContactoEmergencia(model.getContactoEmergencia());
        if (model.getTarjetas() != null) {
            model.getTarjetas().forEach(t -> entity.getTarjetas().add(tarjetaMapper.toEntity(t, entity)));
        }
        return entity;
    }

    public ClienteEntity toEntity(RegistroClienteDto dto) {
        if (dto == null) {
            return null;
        }
        ClienteEntity entity = new ClienteEntity();
        entity.setCorreo(dto.getCorreo());
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setDni(dto.getDni());
        entity.setTelefono(dto.getTelefono());
        entity.setFechaNacimiento(dto.getFechaNacimiento());
        entity.setContactoEmergencia(dto.getContactoEmergencia());
        return entity;
    }

    public void actualizar(ClienteEntity entity, PerfilClienteDto dto) {
        entity.setNombre(dto.getNombre());
        entity.setApellido(dto.getApellido());
        entity.setCorreo(dto.getCorreo());
        entity.setTelefono(dto.getTelefono());
        entity.setContactoEmergencia(dto.getContactoEmergencia());
    }

    public PerfilClienteDto toPerfilDto(ClienteEntity entity) {
        if (entity == null) {
            return null;
        }
        PerfilClienteDto dto = new PerfilClienteDto();
        dto.setNombre(entity.getNombre());
        dto.setApellido(entity.getApellido());
        dto.setCorreo(entity.getCorreo());
        dto.setTelefono(entity.getTelefono());
        dto.setContactoEmergencia(entity.getContactoEmergencia());
        return dto;
    }
}