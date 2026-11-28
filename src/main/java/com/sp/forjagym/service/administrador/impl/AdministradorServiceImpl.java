package com.sp.forjagym.service.administrador.impl;

import com.sp.forjagym.entity.admin.AdminEntity;
import com.sp.forjagym.entity.admin.HorarioAdminEntity;
import com.sp.forjagym.mapper.admin.AdminMapper;
import com.sp.forjagym.model.admin.Admin;
import com.sp.forjagym.Repository.admin.AdminRepository;
import com.sp.forjagym.Repository.admin.HorarioAdminRepository;
import com.sp.forjagym.service.administrador.AdministradorService;
import com.sp.forjagym.service.administrador.HorarioAdminService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AdministradorServiceImpl implements AdministradorService {
    private final AdminRepository administradorRepository;
    private final HorarioAdminService horarioAdminService;
    private final AdminMapper administradorMapper;

    public AdministradorServiceImpl(AdminRepository administradorRepository, HorarioAdminService horarioAdminService, AdminMapper administradorMapper) {
        this.administradorRepository = administradorRepository;
        this.horarioAdminService = horarioAdminService;
        this.administradorMapper = administradorMapper;
    }

    @Override
    public List<Admin> listarTodos() {
        return administradorMapper.toModelList(administradorRepository.findAll());
    }

    @Override
    public Admin buscarPorId(Long id) {
        AdminEntity entity = administradorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El administrador no existe"));
        return administradorMapper.toModel(entity);
    }

    @Override
    public Admin buscarPorCodigo(String codigo) {
        if (codigo == null) return null;
        return administradorRepository.findByCodigo(codigo.trim().toUpperCase())
                .map(administradorMapper::toModelParaLogin)
                .orElse(null);
    }

    @Override
    public boolean estaEnHorario(Long idAdmin, LocalDateTime momento) {
        return finDeTurno(idAdmin, momento) != null;
    }

    @Override
    public LocalDateTime finDeTurno(Long idAdmin, LocalDateTime momento) {
        DayOfWeek dia = momento.getDayOfWeek();
        LocalTime hora = momento.toLocalTime();

        return horarioAdminService.listarPorAdministrador(idAdmin).stream()
                .filter(h -> h.estaDisponible(dia, hora))
                .findFirst()
                .map(h -> momento.toLocalDate().atTime(h.getHoraFin()))
                .orElse(null);
    }
}
