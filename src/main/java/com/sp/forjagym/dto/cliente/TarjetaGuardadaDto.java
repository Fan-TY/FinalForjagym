package com.sp.forjagym.dto.cliente;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TarjetaGuardadaDto {

    @NotNull
    @Positive
    private Long idTarjeta;

    public Long getIdTarjeta() { return idTarjeta; }
    public void setIdTarjeta(Long idTarjeta) { this.idTarjeta = idTarjeta; }
}