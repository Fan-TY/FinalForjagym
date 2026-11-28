package com.sp.forjagym.Repository.compra;

import com.sp.forjagym.entity.compra.DetalleCompraEntity;
import com.sp.forjagym.model.compra.EstadoSuscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DetalleCompraRepository extends JpaRepository<DetalleCompraEntity, Long> {
    Optional<DetalleCompraEntity> findByCompra_ClienteEntity_Id(Long idCliente);
    List<DetalleCompraEntity> findByCompra_ClienteEntity_IdAndEstado(Long idCliente, EstadoSuscripcion estado);
    List<DetalleCompraEntity> findByEstado(EstadoSuscripcion estado);
}