package com.sp.forjagym.service.administrador;

import com.sp.forjagym.model.admin.Admin;

import java.time.LocalDateTime;
import java.util.List;

public interface AdministradorService {
    List<Admin> listarTodos();
    Admin buscarPorId(Long id);
    Admin buscarPorCodigo(String codigo);
    boolean estaEnHorario(Long idAdmin, LocalDateTime momento);
    LocalDateTime finDeTurno(Long idAdmin, LocalDateTime momento);
}