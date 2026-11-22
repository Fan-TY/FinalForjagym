package com.sp.forjagym.entity.cliente;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes")
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "clienteEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TarjetaEntity> tarjetas = new ArrayList<>();

    @Column(nullable = false, length = 50, unique = true)
    private String correo;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, length = 50, unique = true)
    private String dni;

    @Column(nullable = false, length = 50)
    private String telefono;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Column(length = 50)
    private String contactoEmergencia;


    public ClienteEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContactoEmergencia() {
        return contactoEmergencia;
    }

    public void setContactoEmergencia(String contactoEmergencia) {
        this.contactoEmergencia = contactoEmergencia;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public List<TarjetaEntity> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(List<TarjetaEntity> tarjetas) {
        this.tarjetas = tarjetas;
    }
}