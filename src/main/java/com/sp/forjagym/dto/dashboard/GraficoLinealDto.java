package com.sp.forjagym.dto.dashboard;

import java.util.List;

public record GraficoLinealDto(String titulo,
                               String descripcion,
                               String unidad,
                               String nombre,
                               List<String> etiquetas,
                               List<Double> valores) {
}