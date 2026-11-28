package com.sp.forjagym.service.compra.impl;

import com.sp.forjagym.Repository.planes.PlanRepository;
import com.sp.forjagym.entity.compra.DetalleCompraEntity;
import com.sp.forjagym.entity.compra.CompraEntity;
import com.sp.forjagym.entity.planes.PlanEntity;
import com.sp.forjagym.mapper.compra.DetalleCompraMapper;
import com.sp.forjagym.mapper.planes.PlanMapper;
import com.sp.forjagym.model.compra.EstadoSuscripcion;
import com.sp.forjagym.model.compra.DetalleCompra;
import com.sp.forjagym.Repository.compra.DetalleCompraRepository;
import com.sp.forjagym.Repository.compra.CompraRepository;
import com.sp.forjagym.service.compra.BoletaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BoletaServiceImpl implements BoletaService {
    private final DetalleCompraRepository detalleCompraRepository;
    private final CompraRepository compraRepository;
    private final DetalleCompraMapper detalleCompraMapper;
    private final PlanMapper planMapper;
    private final PlanRepository planRepository;

    public BoletaServiceImpl(DetalleCompraRepository detalleCompraRepository, CompraRepository compraRepository,
                             DetalleCompraMapper detalleCompraMapper, PlanRepository planRepository, PlanMapper planMapper) {
        this.detalleCompraRepository = detalleCompraRepository;
        this.compraRepository = compraRepository;
        this.detalleCompraMapper = detalleCompraMapper;
        this.planRepository = planRepository;
        this.planMapper = planMapper;
    }

    @Override
    public DetalleCompra generarParaCompra(Long idCompra, Long idPlan) {
        CompraEntity compra = buscarCompra(idCompra);
        PlanEntity plan = planRepository.findById(idPlan).orElseThrow(() -> new IllegalArgumentException("El plan no existe"));
        LocalDate fechaInicio=compra.getFecha().toLocalDate();
        validarSinBoleta(idCompra);

        DetalleCompra boleta = DetalleCompra.emitir(planMapper.toModel(plan),fechaInicio);
        DetalleCompraEntity guardada = detalleCompraRepository.save(detalleCompraMapper.toEntity(boleta, compra));
        return detalleCompraMapper.toModel(guardada);
    }

    @Override
    public DetalleCompra buscarPorId(Long idBoleta) {
        return detalleCompraMapper.toModel(buscarEntidad(idBoleta));
    }

    @Override
    public DetalleCompra buscarPorCompra(Long idCompra) {
        DetalleCompraEntity entity = detalleCompraRepository.findByCompra_ClienteEntity_Id(idCompra)
                .orElseThrow(() -> new RuntimeException("Esa compra no tiene boleta"));
        return detalleCompraMapper.toModel(entity);
    }

    @Override
    public List<DetalleCompra> listar() {
        return detalleCompraMapper.toModelList(detalleCompraRepository.findAll());
    }

    @Override
    public List<DetalleCompra> listarPorCliente(Long idCliente) {
        return detalleCompraMapper.toModelList(
                detalleCompraRepository.findByCompra_ClienteEntity_IdAndEstado(idCliente, EstadoSuscripcion.ACTIVA));
    }

    /*@Override
    public DetalleCompra ocultar(Long idCliente, Long idBoleta) {
        DetalleCompraEntity boleta = buscarEntidad(idBoleta);
        validarPertenencia(boleta, idCliente);

        boleta.ocultar();
        return detalleCompraMapper.toModel(boleta);
    }*/

    private CompraEntity buscarCompra(Long idCompra) {
        return compraRepository.findById(idCompra)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada: " + idCompra));
    }

    private DetalleCompraEntity buscarEntidad(Long idBoleta) {
        return detalleCompraRepository.findById(idBoleta)
                .orElseThrow(() -> new RuntimeException("No existe la boleta " + idBoleta));
    }

    private void validarSinBoleta(Long idCompra) {
        if (detalleCompraRepository.findByCompra_ClienteEntity_Id(idCompra).isPresent()) {
            throw new IllegalStateException("Esta compra ya tiene una boleta");
        }
    }

    private void validarPertenencia(DetalleCompraEntity boleta, Long idCliente) {
        if (!boleta.getCompra().getId().equals(idCliente)) {
            throw new IllegalArgumentException("La boleta no pertenece a este cliente");
        }
    }
}