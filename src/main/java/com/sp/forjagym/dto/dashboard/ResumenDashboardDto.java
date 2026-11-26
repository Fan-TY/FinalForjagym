package com.sp.forjagym.dto.dashboard;

import java.util.List;

public class ResumenDashboardDto {
    private final String rangoSemana;
    private final List<MetricaDashboardDto> metricas;

    public ResumenDashboardDto(String rangoSemana, List<MetricaDashboardDto> metricas) {
        this.rangoSemana = rangoSemana;
        this.metricas = metricas;
    }

    public String getRangoSemana() { return rangoSemana; }
    public List<MetricaDashboardDto> getMetricas() { return metricas; }
}