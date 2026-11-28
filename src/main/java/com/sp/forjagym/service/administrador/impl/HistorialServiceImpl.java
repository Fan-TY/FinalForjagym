/*package com.sp.forjagym.service.administrador.impl;

import com.sp.forjagym.entity.compra.BoletaEntity;
import com.sp.forjagym.entity.compra.CompraEntity;
import com.sp.forjagym.entity.compra.SuscripcionClienteEntity;
import com.sp.forjagym.mapper.HistorialMapper;
import com.sp.forjagym.model.compra.EstadoSusCliente;
import com.sp.forjagym.model.historial.ClienteHistorial;
import com.sp.forjagym.model.historial.PagoHistorial;
import com.sp.forjagym.model.user.Sede;
import com.sp.forjagym.Repository.BoletaRepository;
import com.sp.forjagym.Repository.cliente.ClienteRepository;
import com.sp.forjagym.Repository.CompraRepository;
import com.sp.forjagym.Repository.SuscripcionClienteRepository;
import com.sp.forjagym.Repository.SuscripcionRepository;
import com.sp.forjagym.service.administrador.HistorialService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class HistorialServiceImpl implements HistorialService {

    private final ClienteRepository clienteRepository;
    private final SuscripcionClienteRepository suscripcionClienteRepository;
    private final SuscripcionRepository suscripcionRepository;
    private final CompraRepository compraRepository;
    private final BoletaRepository boletaRepository;
    private final HistorialMapper historialMapper;

    public HistorialServiceImpl(ClienteRepository clienteRepository,
                                SuscripcionClienteRepository suscripcionClienteRepository,
                                SuscripcionRepository suscripcionRepository,
                                CompraRepository compraRepository,
                                BoletaRepository boletaRepository,
                                HistorialMapper historialMapper) {
        this.clienteRepository = clienteRepository;
        this.suscripcionClienteRepository = suscripcionClienteRepository;
        this.suscripcionRepository = suscripcionRepository;
        this.compraRepository = compraRepository;
        this.boletaRepository = boletaRepository;
        this.historialMapper = historialMapper;
    }

    @Override
    public List<ClienteHistorial> clientes(Long idPlan, String estado, Sede sede, String dni) {
        Map<Long, SuscripcionClienteEntity> planPorCliente = new HashMap<>();
        for (SuscripcionClienteEntity sc : suscripcionClienteRepository.findAll()) {
            planPorCliente.merge(sc.getCliente().getId(), sc, this::masRelevante);
        }
        Map<Long, CompraEntity> ultimaCompra = new HashMap<>();
        for (CompraEntity c : compraRepository.findAll()) {
            ultimaCompra.merge(c.getCliente().getId(), c, (a, b) -> a.getId() > b.getId() ? a : b);
        }

        String dniBuscado = (dni == null) ? "" : dni.trim();

        return clienteRepository.findAll().stream()
                .map(c -> historialMapper.toClienteHistorial(c, planPorCliente.get(c.getId()), ultimaCompra.get(c.getId())))
                .filter(f -> idPlan == null || idPlan.equals(f.getIdPlan()))
                .filter(f -> estado == null || estado.isBlank() || estado.equals(f.getEstado()))
                .filter(f -> sede == null || sede.name().equals(f.getSede()))
                .filter(f -> dniBuscado.isEmpty() || f.getDocumento().equals("DNI " + dniBuscado))
                .sorted(Comparator.comparing(ClienteHistorial::getId).reversed())
                .toList();
    }

    @Override
    public List<PagoHistorial> pagos(Long idPlan, String dni) {
        Map<Long, SuscripcionClienteEntity> porCompra = new HashMap<>();
        for (SuscripcionClienteEntity sc : suscripcionClienteRepository.findAll()) {
            if (sc.getCompra() != null) porCompra.put(sc.getCompra().getId(), sc);
        }

        String dniBuscado = (dni == null) ? "" : dni.trim();

        return boletaRepository.findAll().stream()
                .map(b -> historialMapper.toPagoHistorial(b, porCompra.get(b.getCompra().getId())))
                .filter(p -> idPlan == null || idPlan.equals(p.getIdPlan()))
                .filter(p -> dniBuscado.isEmpty() || p.getDocumento().equals("DNI " + dniBuscado))
                .sorted(Comparator.comparing(PagoHistorial::getFecha).reversed())
                .toList();
    }

    @Override
    public PagoHistorial boleta(Long idBoleta) {
        BoletaEntity b = boletaRepository.findById(idBoleta)
                .orElseThrow(() -> new IllegalArgumentException("La boleta no existe"));
        SuscripcionClienteEntity sc = suscripcionClienteRepository
                .findByCompraId(b.getCompra().getId()).stream().findFirst().orElse(null);
        return historialMapper.toPagoHistorial(b, sc);
    }

    @Override
    public Map<String, Long> activosPorPlan() {
        Map<String, Long> resumen = new LinkedHashMap<>();
        suscripcionRepository.findAll().forEach(p -> resumen.put(p.getNombre(), 0L));
        clientes(null, EstadoSusCliente.ACTIVO.name(), null, null)
                .forEach(f -> resumen.merge(f.getPlan(), 1L, Long::sum));
        return resumen;
    }

    // METODOS DE LA IMPL

    // Si un cliente tiene varias suscripciones, se queda con la ACTIVA; si no, con la más reciente
    private SuscripcionClienteEntity masRelevante(SuscripcionClienteEntity a, SuscripcionClienteEntity b) {
        boolean aActiva = a.getEstado() == EstadoSusCliente.ACTIVO;
        boolean bActiva = b.getEstado() == EstadoSusCliente.ACTIVO;
        if (aActiva != bActiva) return aActiva ? a : b;
        return a.getId() > b.getId() ? a : b;
    }
}*/
