package com.sp.forjagym.service.administrador;

import com.sp.forjagym.model.admin.HorarioAdmin;

import java.time.DayOfWeek;
import java.util.List;

public interface HorarioAdminService {
    HorarioAdmin registrar(HorarioAdmin horario);
    List<HorarioAdmin> listarPorAdministrador(Long administradorId);
    List<HorarioAdmin> listarPorDia(DayOfWeek dia);
    HorarioAdmin buscarPorId(Long id);
    void eliminar(Long id);
}
