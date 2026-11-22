package com.sp.forjagym.model.compra;

import com.sp.forjagym.model.planes.Plan;

import java.math.BigDecimal;

public class DetalleCompra{
    private Long id;
    private Plan plan;
    private BigDecimal precioUnitario;
    private int cantidad;
    private BigDecimal subtotal;

    public DetalleCompra(Long id, Plan plan, BigDecimal precioUnitario, int cantidad, BigDecimal subtotal) {
        this.id = id;
        this.plan = plan;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
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

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }
}