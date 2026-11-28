package com.sp.forjagym.model.planes;

import com.sp.forjagym.model.admin.Admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    public Plan() {
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

    public void setBeneficios(List<Beneficio> beneficios) {
        this.beneficios = beneficios;
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

    public static List<Beneficio> limpiarBeneficios(List<Beneficio> lista) {
        if (lista == null) return new ArrayList<>();
        return lista.stream()
                .filter(Objects::nonNull)
                .map(Beneficio::getDescripcion)
                .filter(Objects::nonNull)
                .map(d -> d.replaceFirst("^\\s*[-*•✓✔]+\\s*", "").trim())
                .filter(d -> !d.isEmpty())
                .distinct()
                .map(d -> new Beneficio(null, d))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}