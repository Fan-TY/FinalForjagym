package com.sp.forjagym.model.compra;


import com.sp.forjagym.model.cliente.Cliente;
import com.sp.forjagym.model.cliente.Tarjeta;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra{
    private Long id;
    private Cliente cliente;
    private Tarjeta tarjeta;
    private List<DetalleCompra> detalles = new ArrayList<>();
    private LocalDateTime fecha;
    private BigDecimal total;
    private EstadoCompra estado;

    public Compra(Long id, Cliente cliente, Tarjeta tarjeta, List<DetalleCompra> detalles, LocalDateTime fecha, BigDecimal total, EstadoCompra estado) {
        this.id = id;
        this.cliente = cliente;
        this.tarjeta = tarjeta;
        this.detalles = detalles;
        this.fecha = fecha;
        this.total = total;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public List<DetalleCompra> getDetalles() {
        return detalles;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public EstadoCompra getEstado() {
        return estado;
    }
}