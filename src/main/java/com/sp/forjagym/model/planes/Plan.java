package com.sp.forjagym.model.planes;

import com.sp.forjagym.model.admin.Admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Plan {
    private Long id;
    private Admin admin;
    private List<Categoria> categorias = new ArrayList<>();
    private List<Beneficio> beneficios = new ArrayList<>();
    private String nombre;
    private BigDecimal precio;
    private int duracionDias;
    private EstadoPlan estadoPlan;

    public Plan(Long id, Admin admin, List<Categoria> categorias, List<Beneficio> beneficios, String nombre, BigDecimal precio, int duracionDias, EstadoPlan estadoPlan) {
        this.id = id;
        this.admin = admin;
        this.categorias = categorias;
        this.beneficios = beneficios;
        this.nombre = nombre;
        this.precio = precio;
        this.duracionDias = duracionDias;
        this.estadoPlan = estadoPlan;
    }

    public Long getId() {
        return id;
    }

    public Admin getAdmin() {
        return admin;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public List<Beneficio> getBeneficios() {
        return beneficios;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public EstadoPlan getEstadoPlan() {
        return estadoPlan;
    }
}