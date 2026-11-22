package com.sp.forjagym.model.compra;

import java.time.LocalDate;

public class SuscripcionCliente {
    private Long id;
    private DetalleCompra detalleCompra;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoSuscripcion estado;

    public SuscripcionCliente(Long id, DetalleCompra detalleCompra, LocalDate fechaInicio, LocalDate fechaFin, EstadoSuscripcion estado) {
        this.id = id;
        this.detalleCompra = detalleCompra;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public DetalleCompra getDetalleCompra() {
        return detalleCompra;
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
}