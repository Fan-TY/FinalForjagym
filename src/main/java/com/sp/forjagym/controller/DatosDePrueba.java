package com.sp.forjagym.controller;

import com.sp.forjagym.model.admin.Admin;
import com.sp.forjagym.model.admin.HorarioAdmin;
import com.sp.forjagym.model.cliente.Cliente;
import com.sp.forjagym.model.cliente.EstadoTarjeta;
import com.sp.forjagym.model.cliente.Tarjeta;
import com.sp.forjagym.model.cliente.TipoTarjeta;
import com.sp.forjagym.model.compra.Compra;
import com.sp.forjagym.model.compra.DetalleCompra;
import com.sp.forjagym.model.compra.EstadoCompra;
import com.sp.forjagym.model.compra.EstadoSuscripcion;
import com.sp.forjagym.model.planes.Beneficio;
import com.sp.forjagym.model.planes.EstadoPlan;
import com.sp.forjagym.model.planes.Plan;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class DatosDePrueba {

    public static Plan plan() {
        List<Beneficio> beneficios = List.of(
                new Beneficio(1L, "Acceso a la sala de pesas"),
                new Beneficio(2L, "Acceso a la zona de cardio"));
        return new Plan(1L, null, new ArrayList<>(), beneficios,
                "Plan de prueba", new BigDecimal("80.00"), 30, EstadoPlan.activo);
    }

    public static Tarjeta tarjeta() {
        return new Tarjeta(1L, "1111", EstadoTarjeta.activo, TipoTarjeta.VISA, YearMonth.of(2028, 12));
    }

    public static Cliente cliente() {
        return new Cliente(1L, "cliente@forjagym.com", "Carla", "Vega", "12345678", "999888777",
                LocalDate.of(2000, 1, 1), "999111222", "12345678",
                new ArrayList<>(List.of(tarjeta())));
    }

    public static DetalleCompra detalle() {
        return new DetalleCompra(1L, plan(), new BigDecimal("80.00"),
                LocalDate.now(), LocalDate.now().plusDays(30), EstadoSuscripcion.ACTIVA);
    }

    public static Compra compra() {
        return new Compra(1L, cliente(), new ArrayList<>(List.of(detalle())),
                LocalDateTime.now(), new BigDecimal("80.00"), EstadoCompra.PAGADA);
    }

    public static Admin admin() {
        return new Admin(1L, "admin@forjagym.com", "Rosa", "Medina", "87654321", "999777666",
                LocalDate.of(1990, 5, 10), "admin123", new ArrayList<>());
    }

    public static List<HorarioAdmin> horarios() {
        return List.of(
                new HorarioAdmin(1L, DayOfWeek.MONDAY, LocalTime.of(8, 0), LocalTime.of(14, 0), null),
                new HorarioAdmin(2L, DayOfWeek.WEDNESDAY, LocalTime.of(14, 0), LocalTime.of(20, 0), null));
    }
}