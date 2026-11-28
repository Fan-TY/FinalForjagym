package com.sp.forjagym.dto.compra;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PagoDto {

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("MM/yy");

    @NotBlank
    @Pattern(regexp = "^(?:[0-9] ?){13,19}$")
    @Size(max = 23)
    private String numeroTarjeta;

    @NotBlank
    @Pattern(regexp = "^(0[1-9]|1[0-2])/[0-9]{2}$")
    private String vencimiento;

    @NotBlank
    @Pattern(regexp = "^[0-9]{3,4}$")
    private String cvv;

    @NotBlank
    @Size(max = 100)
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String titular;

    @AssertTrue
    private boolean aceptaTerminos;

    @AssertTrue
    public boolean esVencimientoVigente() {
        if (vencimiento == null || !vencimiento.matches("^(0[1-9]|1[0-2])/[0-9]{2}$")) {
            return true;
        }
        try {
            YearMonth fecha = YearMonth.parse(vencimiento, FORMATO);
            return !fecha.isBefore(YearMonth.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public String numeroSinEspacios() {
        return numeroTarjeta == null ? null : numeroTarjeta.replace(" ", "");
    }

    public YearMonth vencimientoComoYearMonth() {
        return vencimiento == null ? null : YearMonth.parse(vencimiento, FORMATO);
    }

    public String getNumeroTarjeta() { return numeroTarjeta; }
    public void setNumeroTarjeta(String numeroTarjeta) { this.numeroTarjeta = numeroTarjeta; }

    public String getVencimiento() { return vencimiento; }
    public void setVencimiento(String vencimiento) { this.vencimiento = vencimiento; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }

    public String getTitular() { return titular; }
    public void setTitular(String titular) { this.titular = titular; }

    public boolean esAceptaTerminos() { return aceptaTerminos; }
    public void setAceptaTerminos(boolean aceptaTerminos) { this.aceptaTerminos = aceptaTerminos; }
}