package com.sp.forjagym.service.cliente;

import com.sp.forjagym.model.cliente.Cliente;

public interface RegistroClienteService {
    RegistroResultado registrar(Cliente cliente, Long idTarjeta, Long idSuscripcion);
}