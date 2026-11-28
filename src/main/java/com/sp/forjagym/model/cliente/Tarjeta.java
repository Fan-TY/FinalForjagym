package com.sp.forjagym.model.cliente;

import java.math.BigDecimal;
import java.time.YearMonth;

public class Tarjeta {
    private Long id;
    private Long clienteId;
    private String numeroEnmascarado;
    private String titular;
    private BigDecimal saldo;
    private EstadoTarjeta estado;
    private TipoTarjeta tipoTarjeta;
    private YearMonth fechaExpiracion;

    public Tarjeta(Long id, Long clienteId, String numeroEnmascarado, String titular, BigDecimal saldo,
                   EstadoTarjeta estado, TipoTarjeta tipoTarjeta, YearMonth fechaExpiracion) {
        this.id = id;
        this.clienteId = clienteId;
        this.numeroEnmascarado = numeroEnmascarado;
        this.titular = titular;
        this.saldo = saldo;
        this.estado = estado;
        this.tipoTarjeta = tipoTarjeta;
        this.fechaExpiracion = fechaExpiracion;
    }

    public Long getId() {
        return id;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public String getNumeroVisible() {
        return "**** " + numeroEnmascarado;
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
    public void setNumeroEnmascarado(String numeroEnmascarado) {
        this.numeroEnmascarado = numeroEnmascarado;
    }

    public EstadoTarjeta getEstado() {
        return estado;
    }

    public TipoTarjeta getTipoTarjeta() {
        return tipoTarjeta;
    }

    public YearMonth getFechaExpiracion() {
        return fechaExpiracion;
    }

    public boolean estaDisponible() {
        return estado == EstadoTarjeta.disponible;
    }

    public boolean estaVencida() {
        return fechaExpiracion != null && fechaExpiracion.isBefore(YearMonth.now());
    }

    public boolean tieneSaldoPara(BigDecimal monto) {
        return saldo != null && monto != null && saldo.compareTo(monto) >= 0;
    }

    public boolean puedeSerUsadaPor(Long idCliente) {
        if (estado == EstadoTarjeta.descartada) {
            return false;
        }
        if (estado == EstadoTarjeta.disponible) {
            return true;
        }
        return clienteId != null && clienteId.equals(idCliente);
    }
}