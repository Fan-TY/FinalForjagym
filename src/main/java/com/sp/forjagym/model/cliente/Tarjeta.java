package com.sp.forjagym.model.cliente;

import java.time.YearMonth;

public class Tarjeta {
    private Long id;
    private String numeroEnmascarado;
    private EstadoTarjeta estado;
    private TipoTarjeta tipoTarjeta;
    private YearMonth fechaExpiracion;

    public Tarjeta(Long id, String numeroEnmascarado, EstadoTarjeta estado, TipoTarjeta tipoTarjeta, YearMonth fechaExpiracion) {
        this.id = id;
        this.numeroEnmascarado = numeroEnmascarado;
        this.estado = estado;
        this.tipoTarjeta = tipoTarjeta;
        this.fechaExpiracion = fechaExpiracion;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroEnmascarado() {
        return numeroEnmascarado;
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
}