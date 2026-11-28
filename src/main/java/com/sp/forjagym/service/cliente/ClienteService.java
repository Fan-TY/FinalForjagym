package com.sp.forjagym.service.cliente;

import com.sp.forjagym.model.cliente.Cliente;

import java.util.List;

public interface ClienteService {
    Cliente registrar(Cliente cliente);
    List<Cliente> listarTodos();
    Cliente buscarPorId(Long idCliente);
    boolean existeEmail(String email);
    Cliente buscarPorEmail(String email);
    Cliente actualizarPerfil(Long idCliente, String nombre, String apellidos, String telefono, String contactoEmergencia, String email);
    boolean existeNumDocumento(String numDocumento);
}


