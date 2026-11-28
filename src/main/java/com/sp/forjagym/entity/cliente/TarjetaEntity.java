package com.sp.forjagym.entity.cliente;

import com.sp.forjagym.model.cliente.EstadoTarjeta;
import com.sp.forjagym.model.cliente.TipoTarjeta;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="tarjetas")
public class TarjetaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private ClienteEntity clienteEntity;

    @Column(name = "numero_enmascarado", nullable = false, length = 4)
    private String numeroEnmascarado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoTarjeta estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tarjeta", nullable = false, length = 20)
    private TipoTarjeta tipoTarjeta;

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDate fechaExpiracion;

    @Column(nullable = false)
    private BigDecimal saldo;

    public TarjetaEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClienteEntity getClienteEntity() {
        return clienteEntity;
    }

    public void setClienteEntity(ClienteEntity clienteEntity) {
        this.clienteEntity = clienteEntity;
    }

    public String getNumeroEnmascarado() {
        return numeroEnmascarado;
    }

    public void setNumeroEnmascarado(String numeroEnmascarado) {
        this.numeroEnmascarado = numeroEnmascarado;
    }

    public EstadoTarjeta getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarjeta estado) {
        this.estado = estado;
    }

    public TipoTarjeta getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(TipoTarjeta tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public BigDecimal getSaldo() { return saldo; }

    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
}