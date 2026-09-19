package com.reservashoteleras.habitaciones.repository;

import com.reservashoteleras.habitaciones.entity.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
}
