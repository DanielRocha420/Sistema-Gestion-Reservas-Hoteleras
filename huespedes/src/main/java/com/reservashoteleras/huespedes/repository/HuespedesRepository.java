package com.reservashoteleras.huespedes.repository;

import com.reservashoteleras.huespedes.entity.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HuespedesRepository extends JpaRepository<Huesped, Long> {
}
