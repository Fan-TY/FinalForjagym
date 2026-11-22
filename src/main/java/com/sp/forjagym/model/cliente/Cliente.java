package com.sp.forjagym.model.cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private Long id;
    private String correo;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String contactoEmergencia;
    private List<Tarjeta> tarjetas = new ArrayList<>();

    public Cliente(Long id, String correo, String nombre, String apellido, String dni, String telefono, LocalDate fechaNacimiento, String contactoEmergencia, List<Tarjeta> tarjetas) {
        this.id = id;
        this.correo = correo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.contactoEmergencia = contactoEmergencia;
        this.tarjetas = tarjetas;
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

    public String getContactoEmergencia() {
        return contactoEmergencia;
    }

    public List<Tarjeta> getTarjetas() {
        return tarjetas;
    }
}