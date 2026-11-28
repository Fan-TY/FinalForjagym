package com.sp.forjagym.service.cliente.impl;

import com.sp.forjagym.model.compra.Compra;
import com.sp.forjagym.model.cliente.Cliente;
import com.sp.forjagym.service.cliente.ClienteService;
import com.sp.forjagym.service.cliente.RegistroClienteService;
import com.sp.forjagym.service.cliente.RegistroResultado;
import com.sp.forjagym.service.compra.BoletaService;
import com.sp.forjagym.service.compra.CompraService;
import com.sp.forjagym.service.compra.SuscripcionClienteService;
import com.sp.forjagym.service.compra.TarjetaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistroClienteServiceImpl implements RegistroClienteService {
    private final ClienteService clienteService;
    private final TarjetaService tarjetaService;
    private final CompraService compraService;
    private final BoletaService boletaService;
    private final SuscripcionClienteService suscripcionClienteService;

    public RegistroClienteServiceImpl(ClienteService clienteService, TarjetaService tarjetaService, CompraService compraService,
                                      BoletaService boletaService, SuscripcionClienteService suscripcionClienteService) {
        this.clienteService = clienteService;
        this.tarjetaService = tarjetaService;
        this.compraService = compraService;
        this.boletaService = boletaService;
        this.suscripcionClienteService = suscripcionClienteService;
    }

    @Override
    @Transactional
    public RegistroResultado registrar(Cliente cliente, Long idTarjeta, Long idPlan) {
        Cliente guardado = clienteService.registrar(cliente);
        tarjetaService.vincular(guardado.getId(), idTarjeta);
        Compra compra = compraService.comprarSuscripcion(guardado.getId(), idTarjeta, idPlan);
        boletaService.generarParaCompra(compra.getId(), idPlan);
        suscripcionClienteService.activarDesdeCompra(guardado.getId(), idPlan, compra.getId());
        return new RegistroResultado(guardado.getId(), idTarjeta, idPlan, compra.getId());
    }
}