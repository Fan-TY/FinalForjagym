package com.sp.forjagym.mapper.historial;

import com.sp.forjagym.dto.historial.ClienteHistorialDto;
import com.sp.forjagym.dto.historial.PagoHistorialDto;
import com.sp.forjagym.entity.cliente.ClienteEntity;
import com.sp.forjagym.entity.cliente.TarjetaEntity;
import com.sp.forjagym.entity.compra.CompraEntity;
import com.sp.forjagym.entity.compra.DetalleCompraEntity;
import com.sp.forjagym.model.cliente.EstadoTarjeta;
import com.sp.forjagym.model.compra.EstadoSuscripcion;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Component
public class HistorialMapper {

    public static final String SIN_PLAN = "SIN_PLAN";
    private static final BigDecimal IGV = new BigDecimal("1.18");

    public ClienteHistorialDto toClienteHistorialDto(ClienteEntity c, DetalleCompraEntity detalle) {
        if (c == null) {
            return null;
        }
        return new ClienteHistorialDto(
                c.getId(),
                c.getNombre() + " " + c.getApellido(),
                c.getCorreo(),
                c.getTelefono(),
                c.getDni(),
                detalle == null ? null : detalle.getPlanEntity().getId(),
                detalle == null ? "Sin plan" : detalle.getPlanEntity().getNombre(),
                estadoEfectivo(detalle),
                detalle == null ? null : detalle.getFechaFin(),
                enmascarar(tarjetaVigente(c))
        );
    }

    public PagoHistorialDto toPagoHistorialDto(DetalleCompraEntity d) {
        if (d == null) {
            return null;
        }
        CompraEntity compra = d.getCompra();
        ClienteEntity c = compra.getClienteEntity();
        BigDecimal total = d.getPrecioUnitario();
        BigDecimal subtotal = total.divide(IGV, 2, RoundingMode.HALF_UP);
        return new PagoHistorialDto(
                d.getId(),
                compra.getId(),
                numComprobante(compra.getId()),
                compra.getFecha(),
                c.getId(),
                c.getNombre() + " " + c.getApellido(),
                c.getDni(),
                d.getPlanEntity().getId(),
                d.getPlanEntity().getNombre(),
                d.getFechaInicio(),
                d.getFechaFin(),
                subtotal,
                total.subtract(subtotal),
                total,
                enmascarar(compra.getTarjetaEntity())
        );
    }

    private String estadoEfectivo(DetalleCompraEntity d) {
        if (d == null) {
            return SIN_PLAN;
        }
        boolean vencida = d.getEstado() == EstadoSuscripcion.ACTIVA
                && d.getFechaFin() != null
                && d.getFechaFin().isBefore(LocalDate.now());
        return vencida ? EstadoSuscripcion.VENCIDA.name() : d.getEstado().name();
    }

    private TarjetaEntity tarjetaVigente(ClienteEntity c) {
        return c.getTarjetas().stream()
                .filter(t -> t.getEstado() == EstadoTarjeta.activo)
                .reduce((a, b) -> a.getId() > b.getId() ? a : b)
                .orElse(null);
    }

    private String enmascarar(TarjetaEntity t) {
        if (t == null || t.getNumeroEnmascarado() == null || t.getNumeroEnmascarado().isBlank()) {
            return "—";
        }
        return "****" + t.getNumeroEnmascarado();
    }

    private String numComprobante(Long idCompra) {
        return "B001-" + String.format("%06d", idCompra);
    }
}