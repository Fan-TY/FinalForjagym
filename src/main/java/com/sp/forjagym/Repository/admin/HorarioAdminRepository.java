package com.sp.forjagym.Repository.admin;

import com.sp.forjagym.entity.admin.HorarioAdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioAdminRepository extends JpaRepository<HorarioAdminEntity, Long> {
    List<HorarioAdminEntity> findByAdminEntity_Id(Long idAdmin);
}