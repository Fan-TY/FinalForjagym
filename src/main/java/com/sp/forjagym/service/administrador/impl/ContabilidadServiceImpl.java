/*package com.sp.forjagym.service.administrador.impl;

import com.sp.forjagym.model.contabilidad.GraficoLineal;
import com.sp.forjagym.model.contabilidad.MetricaSemanal;
import com.sp.forjagym.model.contabilidad.ResumenContable;
import com.sp.forjagym.model.notificacion.EstadoNotificacion;
import com.sp.forjagym.Repository.BoletaRepository;
import com.sp.forjagym.Repository.CompraRepository;
import com.sp.forjagym.Repository.NotificacionRepository;
import com.sp.forjagym.Repository.SuscripcionClienteRepository;
import com.sp.forjagym.service.administrador.ContabilidadService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ContabilidadServiceImpl implements ContabilidadService {

    private static final Locale ES = Locale.forLanguageTag("es-PE");
    private static final DateTimeFormatter DIA_MES = DateTimeFormatter.ofPattern("d MMM", ES);
    private static final int DIAS_SEMANA = 7;
    private static final int TOTAL_DIAS = 14;
    private static final String PLAN_BASICO = "Plan Básico";
    private static final String SOLES = "S/";

    private final BoletaRepository boletaRepository;
    private final CompraRepository compraRepository;
    private final NotificacionRepository notificacionRepository;
    private final SuscripcionClienteRepository suscripcionClienteRepository;

    public ContabilidadServiceImpl(BoletaRepository boletaRepository,
                                   CompraRepository compraRepository,
                                   NotificacionRepository notificacionRepository,
                                   SuscripcionClienteRepository suscripcionClienteRepository) {
        this.boletaRepository = boletaRepository;
        this.compraRepository = compraRepository;
        this.notificacionRepository = notificacionRepository;
        this.suscripcionClienteRepository = suscripcionClienteRepository;
    }

    // ===================== TARJETAS (semana en curso) =====================

    @Override
    public ResumenContable resumen() {
        Datos d = calcular();

        List<MetricaSemanal> metricas = List.of(
                metrica("ingresos", "Ingresos de la semana", d.ingresos(), d, true, "bi-cash-coin", "success"),
                metrica("usuarios", "Usuarios registrados", d.usuarios(), d, false, "bi-person-plus-fill", "primary"),
                metrica("boletas", "Boletas generadas", d.boletas(), d, false, "bi-receipt", "danger"),
                metrica("notificaciones", "Notificaciones enviadas", d.notificaciones(), d, false, "bi-bell-fill", "warning"),
                metrica("basico", "Suscripciones Plan Básico", d.basico(), d, false, "bi-box-seam-fill", "info"));

        String rango = "Semana del " + d.lunes().format(DIA_MES) + " al " + d.lunes().plusDays(6).format(DIA_MES);
        return new ResumenContable(rango, metricas);
    }

    // ===================== GRÁFICOS (JSON): una semana, lunes a domingo =====================

    @Override
    public Map<String, GraficoLineal> graficos() {
        Datos d = calcular();
        String periodo = " durante la presente semana";
        Map<String, GraficoLineal> graficos = new LinkedHashMap<>();

        graficos.put("ingresos", new GraficoLineal(
                "Ingresos (S/.)" + periodo,
                "Dinero cobrado cada día, de lunes a domingo, según las boletas emitidas",
                SOLES, "Ingresos", d.etiquetas(), semanaActual(d.ingresos(), d)));

        graficos.put("usuarios", new GraficoLineal(
                "Usuarios registrados" + periodo,
                "Clientes cuya primera compra de plan fue ese día (las renovaciones no cuentan)",
                "usuarios", "Usuarios registrados", d.etiquetas(), semanaActual(d.usuarios(), d)));

        graficos.put("boletas", new GraficoLineal(
                "Boletas generadas" + periodo,
                "Incluye la primera compra y las renovaciones de plan",
                "boletas", "Boletas", d.etiquetas(), semanaActual(d.boletas(), d)));

        graficos.put("notificaciones", new GraficoLineal(
                "Notificaciones enviadas" + periodo,
                "Notificaciones con estado enviado, según su fecha de envío",
                "notificaciones", "Notificaciones", d.etiquetas(), semanaActual(d.notificaciones(), d)));

        graficos.put("basico", new GraficoLineal(
                "Suscripciones al Plan Básico" + periodo,
                "Veces que se contrató el Plan Básico, renovaciones incluidas",
                "suscripciones", "Plan Básico", d.etiquetas(), semanaActual(d.basico(), d)));

        return graficos;
    }

    // METODOS DE LA PROPIA IMPL
    private record Datos(LocalDate lunes, int diaHoy, List<String> etiquetas, double[] ingresos,
                         double[] boletas, double[] usuarios, double[] notificaciones, double[] basico) {
    }

    private Datos calcular() {
        LocalDate hoy = LocalDate.now();
        LocalDate lunes = lunesDe(hoy);
        LocalDate lunesPasado = lunes.minusWeeks(1);
        LocalDateTime desde = lunesPasado.atStartOfDay();

        // Ingresos y boletas (una sola consulta JPA agrupada por día)
        double[] ingresos = new double[TOTAL_DIAS];
        double[] boletas = new double[TOTAL_DIAS];
        for (Object[] f : boletaRepository.ventasPorDia(desde)) {
            int dia = indiceDia(lunesPasado, fecha(f[0], f[1], f[2]));
            if (dia < 0) continue;
            ingresos[dia] += ((Number) f[3]).doubleValue();
            boletas[dia] += ((Number) f[4]).doubleValue();
        }

        // Usuarios registrados = clientes cuya PRIMERA compra cae ese día
        double[] usuarios = new double[TOTAL_DIAS];
        for (LocalDateTime primeraCompra : compraRepository.primerasCompras(desde)) {
            int dia = indiceDia(lunesPasado, primeraCompra.toLocalDate());
            if (dia >= 0) usuarios[dia]++;
        }

        double[] notificaciones = contarPorDia(
                notificacionRepository.enviadasPorDia(EstadoNotificacion.ENVIADO, lunesPasado), lunesPasado);

        double[] basico = contarPorDia(
                suscripcionClienteRepository.suscripcionesPorDiaDelPlan(PLAN_BASICO, lunesPasado), lunesPasado);

        List<String> etiquetas = new ArrayList<>();
        for (int i = 0; i < DIAS_SEMANA; i++) {
            etiquetas.add(etiquetaDia(lunes.plusDays(i)));
        }

        return new Datos(lunes, hoy.getDayOfWeek().getValue() - 1, etiquetas,
                ingresos, boletas, usuarios, notificaciones, basico);
    }


    private List<Double> semanaActual(double[] serie, Datos d) {
        List<Double> valores = new ArrayList<>();
        for (int i = 0; i < DIAS_SEMANA; i++) {
            valores.add(i > d.diaHoy() ? null : Math.round(serie[DIAS_SEMANA + i] * 100.0) / 100.0);
        }
        return valores;
    }


    private MetricaSemanal metrica(String clave, String titulo, double[] serie, Datos d,
                                   boolean dinero, String icono, String color) {
        double actual = sumar(serie, DIAS_SEMANA, DIAS_SEMANA + d.diaHoy());
        double anterior = sumar(serie, 0, d.diaHoy());
        Double variacion = anterior == 0 ? null : Math.round((actual - anterior) / anterior * 1000.0) / 10.0;
        return new MetricaSemanal(clave, titulo, texto(actual, dinero), texto(anterior, dinero),
                variacion, icono, color);
    }

    private double sumar(double[] serie, int desde, int hasta) {
        double total = 0;
        for (int i = desde; i <= hasta; i++) total += serie[i];
        return total;
    }

    private String texto(double valor, boolean dinero) {
        return dinero
                ? String.format(Locale.US, "S/ %,.2f", valor)
                : String.format(Locale.US, "%,d", Math.round(valor));
    }

    // filas: [LocalDate, cantidad]
    private double[] contarPorDia(List<Object[]> filas, LocalDate lunesPasado) {
        double[] dias = new double[TOTAL_DIAS];
        for (Object[] f : filas) {
            int dia = indiceDia(lunesPasado, (LocalDate) f[0]);
            if (dia >= 0) dias[dia] += ((Number) f[1]).doubleValue();
        }
        return dias;
    }

    private LocalDate lunesDe(LocalDate fecha) {
        return fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }


    private int indiceDia(LocalDate lunesPasado, LocalDate fecha) {
        long dia = ChronoUnit.DAYS.between(lunesPasado, fecha);
        return (dia >= 0 && dia < TOTAL_DIAS) ? (int) dia : -1;
    }


    private String etiquetaDia(LocalDate dia) {
        String nombre = dia.getDayOfWeek().getDisplayName(TextStyle.SHORT, ES).replace(".", "");
        return Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1) + " " + dia.getDayOfMonth();
    }

    private LocalDate fecha(Object anio, Object mes, Object dia) {
        return LocalDate.of(((Number) anio).intValue(), ((Number) mes).intValue(), ((Number) dia).intValue());
    }
}*/