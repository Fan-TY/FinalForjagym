package com.sp.forjagym.service.compra.impl;

import com.sp.forjagym.entity.cliente.TarjetaEntity;
import com.sp.forjagym.entity.cliente.ClienteEntity;
import com.sp.forjagym.mapper.cliente.TarjetaMapper;
import com.sp.forjagym.model.cliente.EstadoTarjeta;
import com.sp.forjagym.model.cliente.Tarjeta;
import com.sp.forjagym.Repository.cliente.ClienteRepository;
import com.sp.forjagym.Repository.cliente.TarjetaRepository;
import com.sp.forjagym.service.compra.TarjetaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.time.YearMonth;
import java.util.List;

@Service
public class TarjetaServiceImpl implements TarjetaService {

    private final TarjetaRepository tarjetaRepository;
    private final ClienteRepository clienteRepository;
    private final TarjetaMapper tarjetaMapper;

    public TarjetaServiceImpl(TarjetaRepository tarjetaRepository, ClienteRepository clienteRepository, TarjetaMapper tarjetaMapper) {
        this.tarjetaRepository = tarjetaRepository;
        this.clienteRepository = clienteRepository;
        this.tarjetaMapper = tarjetaMapper;
    }

    @Override
    @Transactional
    public Tarjeta guardar(Long idCliente, TarjetaEntity tarjetaEntity) {
        validarTarjeta(tarjetaEntity);
        tarjetaEntity.setNumeroEnmascarado(normalizar(tarjetaEntity.getNumeroEnmascarado()));

        TarjetaEntity tarjetaEntiti = obtenerOCrearTarjeta(tarjetaEntity);
        return tarjetaMapper.toModel(tarjetaEntiti);
    }

    @Override
    @Transactional(readOnly = true)
    public Tarjeta buscarPorId(Long id) {
        return tarjetaRepository.findById(id)
                .map(tarjetaMapper::toModel)
                .orElseThrow(() -> new RuntimeException("Tarjeta no encontrada: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Tarjeta buscarPorNumero(String numero) {
        return tarjetaRepository.findByNumero(normalizar(numero))
                .map(tarjetaMapper::toModel)
                .orElseThrow(() -> new RuntimeException("Tarjeta no registrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tarjeta> listar() {
        return tarjetaMapper.toModelList(tarjetaRepository.findAll());
    }

    /*@Override
    @Transactional(readOnly = true)
    public List<Tarjeta> listarPorCliente(Long idCliente) {
        return clienteTarjetaRepository
                .findByClienteIdAndEstado(idCliente, EstadoTarjeta.ACTIVA).stream()
                .map(v -> tarjetaMapper.toModel(v.getTarjeta()))
                .toList();
    }

    @Override
    @Transactional
    public void descartarTarjeta(Long idCliente, Long idTarjeta) {
        ClienteTarjetaEntity vinculo = clienteTarjetaRepository
                .findByClienteIdAndTarjetaId(idCliente, idTarjeta)
                .orElseThrow(() -> new RuntimeException("El cliente no tiene esta tarjeta"));

        vinculo.descartar();
        clienteTarjetaRepository.save(vinculo);
    }*/

    @Override
    @Transactional
    public void vincular(Long idCliente, Long idTarjeta) {
        ClienteEntity cliente = buscarCliente(idCliente);
        TarjetaEntity tarjeta = tarjetaRepository.findById(idTarjeta)
                .orElseThrow(() -> new IllegalArgumentException("La tarjeta seleccionada no existe"));

        if (tarjeta.getClienteEntity() != null
                && !tarjeta.getClienteEntity().getId().equals(idCliente)) {
            throw new IllegalStateException("La tarjeta ya pertenece a otro cliente");
        }

        tarjeta.setClienteEntity(cliente);
        tarjeta.setEstado(EstadoTarjeta.activo);
        tarjetaRepository.save(tarjeta);
    }

    @Override
    @Transactional(readOnly = true)
    public Tarjeta validarPago(Long idCliente, String numero, String titular, String vencimiento, String cvv) {
        String n = normalizar(numero);
        if (!n.matches("\\d{13,19}"))
            throw new IllegalArgumentException("El número de tarjeta debe tener entre 13 y 19 dígitos");
        if (vencimiento == null || !vencimiento.trim().matches("(0[1-9]|1[0-2])/\\d{2}"))
            throw new IllegalArgumentException("El vencimiento debe tener formato MM/AA");
        if (cvv == null || !cvv.trim().matches("\\d{3,4}"))
            throw new IllegalArgumentException("El CVV debe tener 3 o 4 dígitos");
        if (titular == null || titular.isBlank())
            throw new IllegalArgumentException("El nombre del titular es obligatorio");

        final String NO_VALIDA = "Los datos ingresados no corresponden a una tarjeta válida";

        String ultimos = n.substring(n.length() - 4);
        String[] mv = vencimiento.trim().split("/");
        YearMonth vence = YearMonth.of(2000 + Integer.parseInt(mv[1]), Integer.parseInt(mv[0]));

        TarjetaEntity t = tarjetaRepository
                .findByClienteEntityIdAndNumeroEnmascarado(idCliente, ultimos).stream()
                .filter(x -> x.getEstado() == EstadoTarjeta.activo)
                .filter(x -> YearMonth.from(x.getFechaExpiracion()).equals(vence))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(NO_VALIDA));

        if (vence.isBefore(YearMonth.now()))
            throw new IllegalArgumentException("La tarjeta está vencida");

        return tarjetaMapper.toModel(t);
    }

    private void validarTarjeta(TarjetaEntity tarjetaEntity) {
        if (tarjetaEntity == null) {
            throw new IllegalArgumentException("Los datos de la tarjeta son obligatorios");
        }
        if (tarjetaEntity.getClienteEntity() == null) {
            throw new IllegalArgumentException("El titular es obligatorio");
        }
        if (tarjetaEntity.getTipoTarjeta() == null) {
            throw new IllegalArgumentException("El tipo de tarjeta es obligatorio");
        }
        if (tarjetaEntity.getNumeroEnmascarado() == null || tarjetaEntity.getNumeroEnmascarado().isBlank()) {
            throw new IllegalArgumentException("El número de tarjeta es obligatorio");
        }

        String numero = normalizar(tarjetaEntity.getNumeroEnmascarado());
        if (!numero.matches("\\d{13,19}")) {
            throw new IllegalArgumentException("El número de tarjeta debe tener entre 13 y 19 dígitos");
        }
        if (tarjetaEntity.getSaldo() == null || tarjetaEntity.getSaldo().signum() < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo");
        }
    }

    private TarjetaEntity obtenerOCrearTarjeta(TarjetaEntity tarjetaEntity) {
        return tarjetaRepository.findByNumero(tarjetaEntity.getNumeroEnmascarado())
                .orElseGet(() -> crear(tarjetaEntity));
    }

    private TarjetaEntity crear(TarjetaEntity tarjeta) {
        Long idCliente = tarjeta.getClienteEntity().getId();

        return tarjetaRepository
                .findByClienteEntityIdAndNumeroEnmascarado(idCliente, tarjeta.getNumeroEnmascarado())
                .stream()
                .filter(t -> t.getFechaExpiracion().equals(tarjeta.getFechaExpiracion()))
                .findFirst()
                .orElseGet(() -> tarjetaRepository.saveAndFlush(tarjeta));
    }

    /*private void vincularConCliente(ClienteEntity cliente, TarjetaEntity tarjetaEntity) {
        ClienteTarjetaEntity vinculo = clienteTarjetaRepository
                .findByClienteIdAndTarjetaId(cliente.getId(), tarjetaEntity.getId())
                .orElseGet(() -> new ClienteTarjetaEntity(cliente, tarjetaEntity));

        vinculo.activar();
        clienteTarjetaRepository.save(vinculo);
    }*/

    private void validarVencimiento(int mes, int anio) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes de expiración debe estar entre 1 y 12");
        }
        int anioCompleto = anio < 100 ? 2000 + anio : anio;
        if (YearMonth.of(anioCompleto, mes).isBefore(YearMonth.now())) {
            throw new IllegalArgumentException("La tarjeta está vencida");
        }
    }

    private ClienteEntity buscarCliente(Long idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    private String limpiarNombre(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").trim().replaceAll("\\s+", " ");
    }

    private String normalizar(String numero) {
        return numero == null ? "" : numero.replaceAll("[\\s-]", "");
    }
}