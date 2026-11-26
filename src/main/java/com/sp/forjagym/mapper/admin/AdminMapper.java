package com.sp.forjagym.mapper.admin;

import com.sp.forjagym.entity.admin.AdminEntity;
import com.sp.forjagym.model.admin.Admin;
import com.sp.forjagym.model.admin.HorarioAdmin;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminMapper {

    private final HorarioAdminMapper horarioAdminMapper;

    public AdminMapper(HorarioAdminMapper horarioAdminMapper) {
        this.horarioAdminMapper = horarioAdminMapper;
    }

    public Admin toModel(AdminEntity entity) {
        return construir(entity, null);
    }

    public Admin toModelParaLogin(AdminEntity entity) {
        return construir(entity, entity == null ? null : entity.getPassword());
    }

    public List<Admin> toModelList(List<AdminEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(this::toModel).toList();
    }

    public AdminEntity toEntity(Admin model) {
        if (model == null) {
            return null;
        }
        AdminEntity entity = new AdminEntity();
        entity.setId(model.getId());
        entity.setCorreo(model.getCorreo());
        entity.setNombre(model.getNombre());
        entity.setApellido(model.getApellido());
        entity.setDni(model.getDni());
        entity.setTelefono(model.getTelefono());
        entity.setPassword(model.getPassword());
        if (model.getHorarios() != null) {
            model.getHorarios().forEach(h -> entity.getHorarios().add(horarioAdminMapper.toEntity(h, entity)));
        }
        return entity;
    }

    private Admin construir(AdminEntity entity, String password) {
        if (entity == null) {
            return null;
        }
        List<HorarioAdmin> horarios = entity.getHorarios().stream()
                .map(horarioAdminMapper::toModel)
                .toList();
        return new Admin(
                entity.getId(),
                entity.getCorreo(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getDni(),
                entity.getTelefono(),
                null,
                password,
                horarios
        );
    }
}