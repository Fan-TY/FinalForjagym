package com.sp.forjagym.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.time.LocalDate;

public class RegistroClienteDto implements Serializable {

    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String nombre;

    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "^[\\p{L} .'-]+$")
    private String apellido;

    @NotBlank
    @Email
    @Size(max = 50)
    private String correo;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9 ]{9,15}$")
    @Size(max = 50)
    private String telefono;

    @NotBlank
    @Pattern(regexp = "^[0-9]{8}$")
    private String dni;

    @NotNull
    @Past
    private LocalDate fechaNacimiento;

    @NotBlank
    @Pattern(regexp = "^\\+?[0-9 ]{9,15}$")
    @Size(max = 50)
    private String contactoEmergencia;

    public void normalizar() {
        nombre = limpiar(nombre);
        apellido = limpiar(apellido);
        correo = correo == null ? null : correo.trim().toLowerCase();
        telefono = limpiar(telefono);
        dni = limpiar(dni);
        contactoEmergencia = limpiar(contactoEmergencia);
    }

    private static String limpiar(String s) { return s == null ? null : s.trim(); }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getContactoEmergencia() { return contactoEmergencia; }
    public void setContactoEmergencia(String contactoEmergencia) { this.contactoEmergencia = contactoEmergencia; }
}