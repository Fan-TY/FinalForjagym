package com.sp.forjagym.model.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Admin {
    private Long id;
    private String correo;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String password;
    private List<HorarioAdmin> horarios = new ArrayList<>();

    public Admin(Long id, String correo, String nombre, String apellido, String dni, String telefono, LocalDate fechaNacimiento, String password, List<HorarioAdmin> horarios) {
        this.id = id;
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.password = password;
        this.horarios = horarios;
    }

    public Long getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDni() {
        return dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getPassword() {
        return password;
    }

    public List<HorarioAdmin> getHorarios() {
        return horarios;
    }
}