package com.sp.forjagym.service.compra;

import com.sp.forjagym.model.compra.DetalleCompra;

import java.util.List;

public interface BoletaService {
    DetalleCompra generarParaCompra(Long idCompra, Long idPlan);
    DetalleCompra buscarPorId(Long idBoleta);
    DetalleCompra buscarPorCompra(Long idCompra);
    List<DetalleCompra> listar();

    List<DetalleCompra> listarPorCliente(Long idCliente);
    //DetalleCompra ocultar(Long idCliente, Long idBoleta);
}