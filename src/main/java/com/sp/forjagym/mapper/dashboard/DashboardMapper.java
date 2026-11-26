package com.sp.forjagym.mapper.dashboard;

import com.sp.forjagym.dto.dashboard.GraficoLinealDto;
import com.sp.forjagym.dto.dashboard.MetricaDashboardDto;
import com.sp.forjagym.dto.dashboard.ResumenDashboardDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class DashboardMapper {

    public static final int DIAS_SEMANA = 7;
    public static final int TOTAL_DIAS = 14;

    private static final Locale ES = Locale.forLanguageTag("es-PE");
    private static final DateTimeFormatter DIA_MES = DateTimeFormatter.ofPattern("d MMM", ES);

    public MetricaDashboardDto toMetrica(String clave, String titulo, double actual, double anterior,
                                         boolean dinero, String icono, String color) {
        Double variacion = anterior == 0 ? null : Math.round((actual - anterior) / anterior * 1000.0) / 10.0;
        return new MetricaDashboardDto(clave, titulo, texto(actual, dinero), texto(anterior, dinero),
                variacion, icono, color);
    }

    public ResumenDashboardDto toResumen(LocalDate lunes, List<MetricaDashboardDto> metricas) {
        String rango = "Semana del " + lunes.format(DIA_MES) + " al " + lunes.plusDays(6).format(DIA_MES);
        return new ResumenDashboardDto(rango, metricas);
    }

    public GraficoLinealDto toGrafico(String titulo, String descripcion, String unidad, String nombre,
                                      List<String> etiquetas, double[] serie14, int diaHoy) {
        List<Double> valores = new ArrayList<>();
        for (int i = 0; i < DIAS_SEMANA; i++) {
            valores.add(i > diaHoy ? null : Math.round(serie14[DIAS_SEMANA + i] * 100.0) / 100.0);
        }
        return new GraficoLinealDto(titulo, descripcion, unidad, nombre, etiquetas, valores);
    }

    public List<String> toEtiquetas(LocalDate lunes) {
        List<String> etiquetas = new ArrayList<>();
        for (int i = 0; i < DIAS_SEMANA; i++) {
            etiquetas.add(etiquetaDia(lunes.plusDays(i)));
        }
        return etiquetas;
    }

    private String etiquetaDia(LocalDate dia) {
        String nombre = dia.getDayOfWeek().getDisplayName(TextStyle.SHORT, ES).replace(".", "");
        return Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1) + " " + dia.getDayOfMonth();
    }

    private String texto(double valor, boolean dinero) {
        return dinero
                ? String.format(Locale.US, "S/ %,.2f", valor)
                : String.format(Locale.US, "%,d", Math.round(valor));
    }
}