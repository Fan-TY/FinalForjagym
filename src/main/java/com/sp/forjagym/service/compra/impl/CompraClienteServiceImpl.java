package com.sp.forjagym.service.compra.impl;

import com.sp.forjagym.model.compra.Compra;
import com.sp.forjagym.service.compra.BoletaService;
import com.sp.forjagym.service.compra.CompraClienteService;
import com.sp.forjagym.service.compra.CompraService;
import com.sp.forjagym.service.compra.SuscripcionClienteService;
import com.sp.forjagym.service.compra.TarjetaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompraClienteServiceImpl implements CompraClienteService {

    private final TarjetaService tarjetaService;
    private final CompraService compraService;
    private final BoletaService boletaService;
    private final SuscripcionClienteService suscripcionClienteService;

    public CompraClienteServiceImpl(TarjetaService tarjetaService,
                                    CompraService compraService,
                                    BoletaService boletaService,
                                    SuscripcionClienteService suscripcionClienteService) {
        this.tarjetaService = tarjetaService;
        this.compraService = compraService;
        this.boletaService = boletaService;
        this.suscripcionClienteService = suscripcionClienteService;
    }

    @Override
    @Transactional
    public Compra comprarPlan(Long idCliente, Long idPlan, Long idTarjeta) {
        tarjetaService.vincular(idCliente, idTarjeta);
        Compra compra = compraService.comprarSuscripcion(idCliente, idTarjeta, idPlan);
        boletaService.generarParaCompra(compra.getId(), idPlan);
        suscripcionClienteService.activarDesdeCompra(idCliente, idPlan, compra.getId());
        return compra;
    }
}