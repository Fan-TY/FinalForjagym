/*package com.sp.forjagym.service.compra.impl;

import com.sp.forjagym.entity.compra.CompraEntity;
import com.sp.forjagym.entity.compra.SuscripcionClienteEntity;
import com.sp.forjagym.entity.planes.PlanEntity;
import com.sp.forjagym.entity.cliente.ClienteEntity;
import com.sp.forjagym.mapper.SuscripcionClienteMapper;
import com.sp.forjagym.model.compra.EstadoSusCliente;
import com.sp.forjagym.model.compra.SuscripcionCliente;
import com.sp.forjagym.Repository.cliente.ClienteRepository;
import com.sp.forjagym.Repository.compra.CompraRepository;
import com.sp.forjagym.Repository.SuscripcionClienteRepository;
import com.sp.forjagym.Repository.planes.PlanRepository;
import com.sp.forjagym.service.compra.SuscripcionClienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class SuscripcionClienteServiceImpl implements SuscripcionClienteService {

    static final String MSG_YA_TIENE_ACTIVA =
            "El cliente ya tiene una suscripción activa. Debe cancelarla antes de contratar otra";

    private final SuscripcionClienteRepository suscripcionClienteRepository;
    private final ClienteRepository clienteRepository;
    private final SuscripcionRepository suscripcionRepository;
    private final CompraRepository compraRepository;
    private final SuscripcionClienteMapper suscripcionClienteMapper;

    public SuscripcionClienteServiceImpl(SuscripcionClienteRepository suscripcionClienteRepository, ClienteRepository clienteRepository, SuscripcionRepository suscripcionRepository, CompraRepository compraRepository, SuscripcionClienteMapper suscripcionClienteMapper) {
        this.suscripcionClienteRepository = suscripcionClienteRepository;
        this.clienteRepository = clienteRepository;
        this.suscripcionRepository = suscripcionRepository;
        this.compraRepository = compraRepository;
        this.suscripcionClienteMapper = suscripcionClienteMapper;
    }

    @Override
    @Transactional
    public SuscripcionCliente activarDesdeCompra(Long idCliente, Long idSuscripcion, Long idCompra) {
        // REGLA DE NEGOCIO: solo una suscripción activa a la vez
        if (tieneActiva(idCliente)) {
            throw new IllegalStateException(MSG_YA_TIENE_ACTIVA);
        }

        ClienteEntity cliente = buscarCliente(idCliente);
        SuscripcionEntity plan = buscarPlan(idSuscripcion);
        CompraEntity compra = buscarCompra(idCompra);

        SuscripcionClienteEntity sc = construirSuscripcion(cliente, plan, compra);
        return suscripcionClienteMapper.toModel(suscripcionClienteRepository.save(sc));
    }

    @Override
    @Transactional(readOnly = true)
    public SuscripcionCliente buscarPorId(Long id) {
        return suscripcionClienteMapper.toModel(buscarEntidad(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionCliente> listarPorCliente(Long idCliente) {
        return suscripcionClienteMapper.toModelList(
                suscripcionClienteRepository.findByClienteId(idCliente));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SuscripcionCliente> listarActivasPorCliente(Long idCliente) {
        return suscripcionClienteMapper.toModelList(
                suscripcionClienteRepository.findByClienteIdAndEstado(idCliente, EstadoSusCliente.ACTIVO));
    }


    @Override
    @Transactional(readOnly = true)
    public boolean tieneActiva(Long idCliente) {
        LocalDate hoy = LocalDate.now();
        return suscripcionClienteRepository
                .findByClienteIdAndEstado(idCliente, EstadoSusCliente.ACTIVO).stream()
                .anyMatch(sc -> sc.getFechaFin() == null || !sc.getFechaFin().isBefore(hoy));
    }

    @Override
    @Transactional
    public SuscripcionCliente cancelar(Long id) {
        SuscripcionClienteEntity sc = buscarEntidad(id);
        sc.cancelar();
        return suscripcionClienteMapper.toModel(sc);
    }


    //METODOS DE LA PROPIA IMPL

    private SuscripcionClienteEntity buscarEntidad(Long id) {
        return suscripcionClienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe la suscripción " + id));
    }

    private ClienteEntity buscarCliente(Long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    private SuscripcionEntity buscarPlan(Long idSuscripcion) {
        return suscripcionRepository.findById(idSuscripcion)
                .orElseThrow(() -> new RuntimeException("Debe indicar el plan a contratar"));
    }

    private CompraEntity buscarCompra(Long idCompra) {
        return compraRepository.findById(idCompra)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));
    }
    @Override
    @Transactional(readOnly = true)
    public SuscripcionCliente buscarPorCompra(Long idCompra) {
        List<SuscripcionClienteEntity> lista = suscripcionClienteRepository.findByCompraId(idCompra);
        return lista.isEmpty() ? null : suscripcionClienteMapper.toModel(lista.get(0));
    }

    private SuscripcionClienteEntity construirSuscripcion(ClienteEntity cliente, SuscripcionEntity plan, CompraEntity compra) {
        SuscripcionClienteEntity sc = new SuscripcionClienteEntity();
        sc.setCliente(cliente);
        sc.setSuscripcion(plan);
        sc.setCompra(compra);
        sc.setFechaInicio(LocalDate.now());
        sc.setFechaFin(LocalDate.now().plusMonths(plan.getDuracion()));
        sc.activar();
        return sc;
    }
}*/