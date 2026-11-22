package com.sp.forjagym.model.planes;

public class Beneficio {
    private Long id;
    private String descripcion;

    public Beneficio(Long id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }
}