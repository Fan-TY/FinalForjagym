package com.sp.forjagym.entity.cliente;

import com.sp.forjagym.model.cliente.Estado;
import com.sp.forjagym.model.cliente.TipoTarjeta;
import jakarta.persistence.*;

import java.time.YearMonth;

@Entity
@Table(name="tarjetas")
public class TarjetaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity clienteEntity;

    @Column(name = "numero_enmascarado", nullable = false, length = 4)
    private String numeroEnmascarado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estado estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tarjeta", nullable = false, length = 20)
    private TipoTarjeta tipoTarjeta;

    @Column(name = "fecha_expiracion", nullable = false)
    private YearMonth fechaExpiracion;

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

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public TipoTarjeta getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(TipoTarjeta tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public YearMonth getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(YearMonth fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }
}
