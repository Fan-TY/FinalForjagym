package com.sp.forjagym.entity.compra;

import com.sp.forjagym.model.compra.EstadoSuscripcion;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "suscripcion_cliente")
public class SuscripcionClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "detalle_compra_id", nullable = false, unique = true)
    private DetalleCompraEntity detalleCompra;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoSuscripcion estado;

    public SuscripcionClienteEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DetalleCompraEntity getDetalleCompra() {
        return detalleCompra;
    }

    public void setDetalleCompra(DetalleCompraEntity detalleCompra) {
        this.detalleCompra = detalleCompra;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoSuscripcion getEstado() {
        return estado;
    }

    public void setEstado(EstadoSuscripcion estado) {
        this.estado = estado;
    }
}