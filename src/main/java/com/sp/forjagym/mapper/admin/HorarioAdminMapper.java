package com.sp.forjagym.mapper.admin;

import com.sp.forjagym.entity.admin.AdminEntity;
import com.sp.forjagym.entity.admin.HorarioAdminEntity;
import com.sp.forjagym.model.admin.HorarioAdmin;
import org.springframework.stereotype.Component;

@Component
public class HorarioAdminMapper {

    public HorarioAdmin toModel(HorarioAdminEntity entity) {
        if (entity == null) {
            return null;
        }
        return new HorarioAdmin(
                entity.getId(),
                entity.getDiaSemana(),
                entity.getHoraInicio(),
                entity.getHoraFin(),
                null
        );
    }

    public HorarioAdminEntity toEntity(HorarioAdmin model, AdminEntity adminEntity) {
        if (model == null) {
            return null;
        }
        HorarioAdminEntity entity = new HorarioAdminEntity();
        entity.setId(model.getId());
        entity.setDiaSemana(model.getDiaSemana());
        entity.setHoraInicio(model.getHoraInicio());
        entity.setHoraFin(model.getHoraFin());
        entity.setAdminEntity(adminEntity);
        return entity;
    }
}