package com.sp.forjagym.Repository.cliente;


import com.sp.forjagym.entity.cliente.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>{
    Optional<ClienteEntity> findByCorreo(String correo);
    Optional<ClienteEntity> findByDni(String dni);
    boolean existsByCorreo(String correo);
    boolean existsByDni(String dni);
}
