package com.sp.forjagym.mapper.planes;

import com.sp.forjagym.dto.planes.PlanDto;
import com.sp.forjagym.entity.admin.AdminEntity;
import com.sp.forjagym.entity.planes.BeneficioEntity;
import com.sp.forjagym.entity.planes.PlanEntity;
import com.sp.forjagym.mapper.admin.AdminMapper;
import com.sp.forjagym.model.planes.Beneficio;
import com.sp.forjagym.model.planes.Categoria;
import com.sp.forjagym.model.planes.Plan;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanMapper {

    private final AdminMapper adminMapper;
    private final CategoriaMapper categoriaMapper;
    private final BeneficioMapper beneficioMapper;

    public PlanMapper(AdminMapper adminMapper, CategoriaMapper categoriaMapper, BeneficioMapper beneficioMapper) {
        this.adminMapper = adminMapper;
        this.categoriaMapper = categoriaMapper;
        this.beneficioMapper = beneficioMapper;
    }

    public Plan toModel(PlanEntity entity) {
        if (entity == null) {
            return null;
        }
        List<Categoria> categorias = entity.getCategorias().stream()
                .map(categoriaMapper::toModel)
                .toList();
        List<Beneficio> beneficios = entity.getBeneficios().stream()
                .map(beneficioMapper::toModel)
                .toList();
        return new Plan(
                entity.getId(),
                adminMapper.toModel(entity.getAdminEntity()),
                categorias,
                beneficios,
                entity.getNombre(),
                entity.getPrecio(),
                entity.getDuracionDias(),
                entity.getEstadoPlan()
        );
    }

    public PlanEntity toEntity(Plan model, AdminEntity adminEntity) {
        if (model == null) {
            return null;
        }
        PlanEntity entity = new PlanEntity();
        entity.setId(model.getId());
        entity.setAdminEntity(adminEntity);
        entity.setNombre(model.getNombre());
        entity.setPrecio(model.getPrecio());
        entity.setDuracionDias(model.getDuracionDias());
        entity.setEstadoPlan(model.getEstadoPlan());
        if (model.getCategorias() != null) {
            model.getCategorias().forEach(c -> entity.getCategorias().add(categoriaMapper.toEntity(c, entity)));
        }
        if (model.getBeneficios() != null) {
            model.getBeneficios().forEach(b -> entity.getBeneficios().add(beneficioMapper.toEntity(b, entity)));
        }
        return entity;
    }

    public PlanEntity toEntity(PlanDto dto, AdminEntity adminEntity) {
        if (dto == null) {
            return null;
        }
        PlanEntity entity = new PlanEntity();
        entity.setAdminEntity(adminEntity);
        actualizar(entity, dto);
        return entity;
    }

    public void actualizar(PlanEntity entity, PlanDto dto) {
        entity.setNombre(dto.getNombre());
        entity.setPrecio(dto.getPrecio());
        entity.setDuracionDias(dto.getDuracionDias());
        entity.setEstadoPlan(dto.getEstadoPlan());
        entity.getBeneficios().clear();
        separar(dto.getBeneficios()).forEach(descripcion -> {
            BeneficioEntity beneficio = new BeneficioEntity();
            beneficio.setDescripcion(descripcion);
            beneficio.setPlanEntity(entity);
            entity.getBeneficios().add(beneficio);
        });
    }

    public PlanDto toDto(PlanEntity entity) {
        if (entity == null) {
            return null;
        }
        PlanDto dto = new PlanDto();
        dto.setNombre(entity.getNombre());
        dto.setPrecio(entity.getPrecio());
        dto.setDuracionDias(entity.getDuracionDias());
        dto.setEstadoPlan(entity.getEstadoPlan());
        dto.setBeneficios(entity.getBeneficios().stream()
                .map(BeneficioEntity::getDescripcion)
                .collect(Collectors.joining("\n")));
        return dto;
    }

    private List<String> separar(String texto) {
        if (texto == null) {
            return List.of();
        }
        return Arrays.stream(texto.split("\\R"))
                .map(linea -> linea.replaceFirst("^\\s*[-*•✓✔]+\\s*", "").trim())
                .filter(linea -> !linea.isEmpty())
                .toList();
    }
    public List<Plan> toModelList(List<PlanEntity> entities) {
        if (entities == null) return List.of();
        return entities.stream().map(this::toModel).toList();
    }

    public void reemplazarBeneficios(PlanEntity entity, List<Beneficio> beneficios) {
        entity.getBeneficios().clear();
        if (beneficios != null) {
            beneficios.forEach(b -> entity.getBeneficios().add(beneficioMapper.toEntity(b, entity)));
        }
    }
}