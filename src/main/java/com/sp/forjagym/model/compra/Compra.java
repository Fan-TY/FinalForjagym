package com.sp.forjagym.model.compra;


import com.sp.forjagym.model.cliente.Cliente;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra{
    private Long id;
    private Cliente cliente;
    private List<DetalleCompra> detalles = new ArrayList<>();
    private LocalDateTime fecha;
    private BigDecimal total;
    private EstadoCompra estado;

    public Compra(Long id, Cliente cliente, List<DetalleCompra> detalles, LocalDateTime fecha, BigDecimal total, EstadoCompra estado) {
        this.id = id;
        this.cliente = cliente;
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