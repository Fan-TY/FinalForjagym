package com.sp.forjagym.entity.planes;

import com.sp.forjagym.entity.admin.AdminEntity;
import com.sp.forjagym.model.planes.EstadoPlan;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "planes")
public class PlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "administrador_id", nullable = false)
    private AdminEntity adminEntity;

    @OneToMany(mappedBy = "planEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoriaEntity> categorias = new ArrayList<>();

    @OneToMany(mappedBy = "planEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BeneficioEntity> beneficios = new ArrayList<>();

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "duracion_dias", nullable = false)
    private int duracionDias;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_plan", nullable = false, length = 20)
    private EstadoPlan estadoPlan;

    public PlanEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AdminEntity getAdminEntity() {
        return adminEntity;
    }

    public void setAdminEntity(AdminEntity adminEntity) {
        this.adminEntity = adminEntity;
    }

    public List<CategoriaEntity> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaEntity> categorias) {
        this.categorias = categorias;
    }

    public List<BeneficioEntity> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(List<BeneficioEntity> beneficios) {
        this.beneficios = beneficios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public EstadoPlan getEstadoPlan() {
        return estadoPlan;
    }

    public void setEstadoPlan(EstadoPlan estadoPlan) {
        this.estadoPlan = estadoPlan;
    }
}