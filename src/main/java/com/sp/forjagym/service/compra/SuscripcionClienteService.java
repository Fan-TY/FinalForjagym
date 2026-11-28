package com.sp.forjagym.service.compra;

import com.sp.forjagym.model.compra.DetalleCompra;

import java.util.List;

public interface SuscripcionClienteService {

    DetalleCompra activarDesdeCompra(Long idCliente, Long idSuscripcion, Long idCompra);
    DetalleCompra buscarPorId(Long id);
    List<DetalleCompra> listarPorCliente(Long idCliente);
    List<DetalleCompra> listarActivasPorCliente(Long idCliente);
    boolean tieneActiva(Long idCliente);
    // No se borra: solo pasa a CANCELADO
    DetalleCompra cancelar(Long id);
    DetalleCompra buscarPorCompra(Long idCompra);
}