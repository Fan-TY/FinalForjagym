package com.sp.forjagym.service.cliente;

import java.io.Serializable;

public record RegistroResultado(Long idCliente, Long idTarjeta, Long idSuscripcion, Long idCompra)
        implements Serializable {
}