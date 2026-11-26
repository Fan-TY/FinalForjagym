package com.sp.forjagym.dto.historial;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PagoHistorialDto {
    private final Long idDetalle;
    private final Long idCompra;
    private final String numComprobante;
    private final LocalDateTime fecha;
    private final Long idCliente;
    private final String cliente;
    private final String dni;
    private final Long idPlan;
    private final String plan;
    private final LocalDate fechaInicio;
    private final LocalDate fechaFin;
    private final BigDecimal subtotal;
    private final BigDecimal igv;
    private final BigDecimal total;
    private final String tarjeta;

    public PagoHistorialDto(Long idDetalle, Long idCompra, String numComprobante, LocalDateTime fecha,
                            Long idCliente, String cliente, String dni, Long idPlan, String plan,
                            LocalDate fechaInicio, LocalDate fechaFin,
                            BigDecimal subtotal, BigDecimal igv, BigDecimal total, String tarjeta) {
        this.idDetalle = idDetalle;
        this.idCompra = idCompra;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.cliente = cliente;
        this.dni = dni;
        this.idPlan = idPlan;
        this.plan = plan;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.subtotal = subtotal;
        this.igv = igv;
        this.total = total;
        this.tarjeta = tarjeta;
    }

    public Long getIdDetalle() { return idDetalle; }
    public Long getIdCompra() { return idCompra; }
    public String getNumComprobante() { return numComprobante; }
    public LocalDateTime getFecha() { return fecha; }
    public Long getIdCliente() { return idCliente; }
    public String getCliente() { return cliente; }
    public String getDni() { return dni; }
    public Long getIdPlan() { return idPlan; }
    public String getPlan() { return plan; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getIgv() { return igv; }
    public BigDecimal getTotal() { return total; }
    public String getTarjeta() { return tarjeta; }
}