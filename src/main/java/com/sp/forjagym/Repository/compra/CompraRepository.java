package com.sp.forjagym.Repository.compra;
import com.sp.forjagym.entity.compra.CompraEntity;
import com.sp.forjagym.model.compra.EstadoCompra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CompraRepository extends JpaRepository<CompraEntity, Long> {
    List<CompraEntity> findAllByOrderByFechaDesc();
    List<CompraEntity> findByClienteEntity_IdOrderByFechaDesc(Long idCliente);
    List<CompraEntity> findByEstadoAndFechaBetween(EstadoCompra estado, LocalDateTime desde, LocalDateTime hasta);
}