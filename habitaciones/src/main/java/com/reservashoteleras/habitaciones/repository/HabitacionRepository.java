package com.reservashoteleras.habitaciones.repository;

import com.daniel.commons.enums.EstadoHabitacion;
import com.daniel.commons.enums.EstadoRegistro;
import com.reservashoteleras.habitaciones.entity.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {

    List<Habitacion> findByEstadoRegistro(EstadoRegistro estadoRegistro);


    List<Habitacion> findByEstado(EstadoHabitacion estado);


    Optional<Habitacion> findByNumHabitacionAndEstadoRegistro(Long numHabitacion, EstadoRegistro registro);
}