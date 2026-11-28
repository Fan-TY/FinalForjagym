package com.sp.forjagym.service.compra;

import com.sp.forjagym.model.compra.Compra;

public interface CompraClienteService {

    Compra comprarPlan(Long idCliente, Long idSuscripcion, Long idTarjeta);

}