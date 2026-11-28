package com.sp.forjagym.model.admin;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class HorarioAdmin {

    private Long id;
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Admin admin;


    public HorarioAdmin(Long id, DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFin, Admin admin) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.admin = admin;
    }
    public boolean estaDisponible(DayOfWeek dia, LocalTime hora) {
        return this.diaSemana == dia && !hora.isBefore(horaInicio) &&
                hora.isBefore(horaFin);
    }

    public Long getId() { return id; }

    public DayOfWeek getDiaSemana() { return diaSemana; }

    public LocalTime getHoraInicio() { return horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }

    public Admin getAdmin() {
        return admin;
    }
}