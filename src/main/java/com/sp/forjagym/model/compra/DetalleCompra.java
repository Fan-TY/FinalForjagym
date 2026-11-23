package com.sp.forjagym.model.compra;

import com.sp.forjagym.model.planes.Plan;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DetalleCompra {
    private Long id;
    private Plan plan;
    private BigDecimal precioUnitario;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoSuscripcion estado;

    public DetalleCompra(Long id, Plan plan, BigDecimal precioUnitario,
                         LocalDate fechaInicio, LocalDate fechaFin, EstadoSuscripcion estado) {
        this.id = id;
        this.plan = plan;
        this.precioUnitario = precioUnitario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public Plan getPlan() {
        return plan;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public EstadoSuscripcion getEstado() {
        return estado;
    }

    public EstadoSuscripcion getEstadoActual() {
        if (estado == EstadoSuscripcion.CANCELADA) {
            return EstadoSuscripcion.CANCELADA;
        }
        if (fechaFin != null && LocalDate.now().isAfter(fechaFin)) {
            return EstadoSuscripcion.VENCIDA;
        }
        return EstadoSuscripcion.ACTIVA;
    }
}