package com.sp.forjagym.Repository.cliente;

import com.sp.forjagym.entity.cliente.TarjetaEntity;
import com.sp.forjagym.model.cliente.EstadoTarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TarjetaRepository extends JpaRepository<TarjetaEntity, Long> {
    List<TarjetaEntity> findByClienteEntity_IdAndEstado(Long idCliente, EstadoTarjeta estado);
    Optional<TarjetaEntity> findByIdAndClienteEntity_Id(Long id, Long idCliente);
}
