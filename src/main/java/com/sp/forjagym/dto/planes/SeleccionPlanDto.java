package com.sp.forjagym.dto.planes;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class SeleccionPlanDto {

    @NotNull
    @Positive
    private Long idPlan;

    public Long getIdPlan() { return idPlan; }
    public void setIdPlan(Long idPlan) { this.idPlan = idPlan; }
}