package com.sp.forjagym.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PerfilClienteDto {

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
    @Pattern(regexp = "^\\+?[0-9 ]{9,15}$")
    @Size(max = 50)
    private String contactoEmergencia;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getContactoEmergencia() { return contactoEmergencia; }
    public void setContactoEmergencia(String contactoEmergencia) { this.contactoEmergencia = contactoEmergencia; }
}