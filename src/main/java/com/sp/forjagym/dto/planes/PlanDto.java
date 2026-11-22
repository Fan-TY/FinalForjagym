package com.sp.forjagym.dto.planes;

import com.sp.forjagym.model.planes.EstadoPlan;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class PlanDto {

    @NotBlank
    @Size(max = 50)
    private String nombre;

    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 8, fraction = 2)
    private BigDecimal precio;

    @NotNull
    @Min(1)
    @Max(365)
    private Integer duracionDias;

    @NotNull
    private EstadoPlan estadoPlan;

    @NotBlank
    @Size(max = 2000)
    private String beneficios;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Integer getDuracionDias() { return duracionDias; }
    public void setDuracionDias(Integer duracionDias) { this.duracionDias = duracionDias; }

    public EstadoPlan getEstadoPlan() { return estadoPlan; }
    public void setEstadoPlan(EstadoPlan estadoPlan) { this.estadoPlan = estadoPlan; }

    public String getBeneficios() { return beneficios; }
    public void setBeneficios(String beneficios) { this.beneficios = beneficios; }
}