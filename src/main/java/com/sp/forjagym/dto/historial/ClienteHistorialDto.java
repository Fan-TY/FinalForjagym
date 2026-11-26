package com.sp.forjagym.dto.historial;

import java.time.LocalDate;

public class ClienteHistorialDto {
    private final Long id;
    private final String nombreCompleto;
    private final String correo;
    private final String telefono;
    private final String dni;
    private final Long idPlan;
    private final String plan;
    private final String estado;
    private final LocalDate vence;
    private final String tarjeta;

    public ClienteHistorialDto(Long id, String nombreCompleto, String correo, String telefono, String dni,
                               Long idPlan, String plan, String estado, LocalDate vence, String tarjeta) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.dni = dni;
        this.idPlan = idPlan;
        this.plan = plan;
        this.estado = estado;
        this.vence = vence;
        this.tarjeta = tarjeta;
    }

    public Long getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public String getDni() { return dni; }
    public Long getIdPlan() { return idPlan; }
    public String getPlan() { return plan; }
    public String getEstado() { return estado; }
    public LocalDate getVence() { return vence; }
    public String getTarjeta() { return tarjeta; }
}