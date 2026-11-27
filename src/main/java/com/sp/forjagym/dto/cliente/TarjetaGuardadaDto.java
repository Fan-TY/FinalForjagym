package com.sp.forjagym.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class TarjetaGuardadaDto {

    @NotNull
    @Positive
    private Long idTarjeta;

    @NotBlank
    @Pattern(regexp = "^[0-9]{3,4}$")
    private String cvv;

    public Long getIdTarjeta() { return idTarjeta; }
    public void setIdTarjeta(Long idTarjeta) { this.idTarjeta = idTarjeta; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }
}