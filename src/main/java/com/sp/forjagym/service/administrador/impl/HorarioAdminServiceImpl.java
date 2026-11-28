/*package com.forjagym.forjagym.service.administrador.impl;

import com.forjagym.forjagym.entity.user.AdministradorEntity;
import com.forjagym.forjagym.entity.user.HorarioAdminEntity;

import com.forjagym.forjagym.mapper.HorarioAdminMapper;
import com.forjagym.forjagym.model.user.HorarioAdmin;
import com.forjagym.forjagym.repository.AdministradorRepository;
import com.forjagym.forjagym.repository.HorarioAdminRepository;
import com.forjagym.forjagym.service.administrador.HorarioAdminService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;

@Service
public class HorarioAdminServiceImpl implements HorarioAdminService {
    private final HorarioAdminRepository horarioAdminRepository;
    private final AdministradorRepository administradorRepository;
    private final HorarioAdminMapper horarioMapper;

    public HorarioAdminServiceImpl(HorarioAdminRepository horarioAdminRepository, AdministradorRepository administradorRepository, HorarioAdminMapper horarioMapper) {
        this.horarioAdminRepository = horarioAdminRepository;
        this.administradorRepository = administradorRepository;
        this.horarioMapper = horarioMapper;
    }

    @Override
    public HorarioAdmin registrar(HorarioAdmin horario) {
        if (horario.getAdministrador() == null || horario.getAdministrador().getId() == null) {
            throw new IllegalArgumentException("El horario debe tener un administrador");
        }
        if (!horario.getHoraInicio().isBefore(horario.getHoraFin())) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora fin");
        }

        AdministradorEntity admin = administradorRepository.findById(horario.getAdministrador().getId())
                .orElseThrow(() -> new IllegalArgumentException("El administrador no existe"));

        HorarioAdminEntity guardado = horarioAdminRepository.save(horarioMapper.toEntity(horario, admin));
        return horarioMapper.toModel(guardado);
    }


    @Override
    public List<HorarioAdmin> listarPorAdministrador(Long administradorId) {
        return horarioMapper.toModelList(horarioAdminRepository.findByAdministradorId(administradorId));
    }

    @Override
    public List<HorarioAdmin> listarPorDia(DayOfWeek dia) {
        return horarioMapper.toModelList(horarioAdminRepository.findByDiaSemana(dia));
    }

    @Override
    public HorarioAdmin buscarPorId(Long id) {
        HorarioAdminEntity entity = horarioAdminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El horario no existe"));
        return horarioMapper.toModel(entity);
    }

    @Override
    public void eliminar(Long id) {
        if (!horarioAdminRepository.existsById(id)) {
            throw new IllegalArgumentException("El horario no existe");
        }
        horarioAdminRepository.deleteById(id);
    }
}*/
