package com.sp.forjagym.service.compra.impl;

import com.sp.forjagym.entity.compra.CompraEntity;
import com.sp.forjagym.entity.cliente.TarjetaEntity;
import com.sp.forjagym.entity.planes.PlanEntity;
import com.sp.forjagym.entity.cliente.ClienteEntity;
import com.sp.forjagym.mapper.compra.CompraMapper;
import com.sp.forjagym.model.compra.Compra;
import com.sp.forjagym.Repository.cliente.ClienteRepository;
import com.sp.forjagym.Repository.compra.CompraRepository;
import com.sp.forjagym.Repository.planes.PlanRepository;
import com.sp.forjagym.Repository.cliente.TarjetaRepository;
import com.sp.forjagym.service.compra.CompraService;
import com.sp.forjagym.service.compra.SuscripcionClienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepository;
    private final ClienteRepository clienteRepository;
    private final TarjetaRepository tarjetaRepository;
    private final PlanRepository planRepository;
    private final CompraMapper compraMapper;
    private final SuscripcionClienteService suscripcionClienteService;

    public CompraServiceImpl(CompraRepository compraRepository, ClienteRepository clienteRepository, TarjetaRepository tarjetaRepository,
                             PlanRepository planRepository, CompraMapper compraMapper, SuscripcionClienteService suscripcionClienteService) {
        this.compraRepository = compraRepository;
        this.clienteRepository = clienteRepository;
        this.tarjetaRepository = tarjetaRepository;
        this.planRepository = planRepository;
        this.compraMapper = compraMapper;
        this.suscripcionClienteService = suscripcionClienteService;
    }

    @Override
    @Transactional
    public Compra guardar(Compra compra, Long idTarjeta) {
        validar(compra);
        if (idTarjeta == null) {
            throw new IllegalArgumentException("Debe indicar la tarjeta de pago");
        }
        return registrarCompra(compra.getCliente().getId(), idTarjeta, compra.getTotal());
    }

    @Override
    @Transactional
    public Compra comprarSuscripcion(Long idCliente, Long idTarjeta, Long idSuscripcion) {
        if (suscripcionClienteService.tieneActiva(idCliente)) {
            throw new IllegalStateException(
                    "El cliente ya tiene una suscripción activa. Debe cancelarla antes de contratar otra");
        }

        PlanEntity suscripcion = planRepository.findById(idSuscripcion)
                .orElseThrow(() -> new RuntimeException("Suscripción no encontrada"));

        return registrarCompra(idCliente, idTarjeta, suscripcion.getPrecio());
    }

    @Override
    @Transactional(readOnly = true)
    public Compra buscarPorId(Long id) {
        return compraRepository.findById(id)
                .map(compraMapper::toModel)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> listar() {
        return compraMapper.toModelList(compraRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Compra> listarPorCliente(Long idCliente) {
        return compraMapper.toModelList(compraRepository.findByClienteEntity_IdOrderByFechaDesc(idCliente));
    }

    private Compra registrarCompra(Long idCliente, Long idTarjeta, BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a cero");
        }

        ClienteEntity cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        TarjetaEntity tarjeta = tarjetaRepository.findByIdAndClienteEntity_Id(idTarjeta).orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));

        descontarSaldo(tarjeta, total);

        CompraEntity entity = new CompraEntity();
        entity.setClienteEntity(cliente);
        entity.setTotal(total);
        entity.setFecha(LocalDateTime.now());

        return compraMapper.toModel(compraRepository.save(entity));
    }

    /*private void validarVinculoActivo(Long idCliente, Long idTarjeta) {
        ClienteTarjetaEntity vinculo = clienteTarjetaRepository
                .findByClienteIdAndTarjetaId(idCliente, idTarjeta)
                .orElseThrow(() -> new RuntimeException("La tarjeta no pertenece al cliente"));

        if (vinculo.getEstado() != EstadoTarjeta.ACTIVA) {
            throw new IllegalStateException("La tarjeta fue descartada");
        }
    }*/

    private void descontarSaldo(TarjetaEntity tarjeta, BigDecimal total) {
        BigDecimal saldo = tarjeta.getSaldo() == null ? BigDecimal.ZERO : tarjeta.getSaldo();

        if (saldo.compareTo(total) < 0) {
            throw new IllegalStateException("Saldo insuficiente en la tarjeta");
        }
        tarjeta.setSaldo(saldo.subtract(total));
        tarjetaRepository.save(tarjeta);
    }

    private void validar(Compra compra) {
        if (compra == null) {
            throw new IllegalArgumentException("La compra es obligatoria");
        }
        if (compra.getCliente() == null || compra.getCliente().getId() == null) {
            throw new IllegalArgumentException("La compra debe tener un cliente");
        }
        if (compra.getTotal() == null || compra.getTotal().signum() <= 0) {
            throw new IllegalArgumentException("El total debe ser mayor a cero");
        }
    }
}