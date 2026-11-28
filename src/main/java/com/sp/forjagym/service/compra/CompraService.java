package com.sp.forjagym.service.compra;

import com.sp.forjagym.model.compra.Compra;

import java.util.List;

public interface CompraService {
    Compra guardar(Compra compra, Long idTarjeta);
    Compra comprarSuscripcion(Long idCliente, Long idTarjeta, Long idSuscripcion);
    Compra buscarPorId(Long id);
    List<Compra> listar();
    List<Compra> listarPorCliente(Long idCliente);
}