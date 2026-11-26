package com.sp.forjagym.Repository.admin;

import com.sp.forjagym.entity.admin.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
    Optional<AdminEntity> findByCorreo(String correo);
}
