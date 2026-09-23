package com.reservashoteleras.habitaciones.service;

import com.daniel.commons.dto.habitacion.HabitacionRequest;
import com.daniel.commons.dto.habitacion.HabitacionResponse;
import com.daniel.commons.enums.EstadoHabitacion;
import com.daniel.commons.enums.EstadoRegistro;
import com.daniel.commons.enums.TipoHabitacion;
import com.daniel.commons.exceptions.RecursoNoEncontradoException;
import com.reservashoteleras.habitaciones.entity.Habitacion;
import com.reservashoteleras.habitaciones.mapper.HabitacionMapper;
import com.reservashoteleras.habitaciones.repository.HabitacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {
    private final HabitacionRepository habitacionRepository;
    private final HabitacionMapper habitacionMapper;

    @Override
    public List<HabitacionResponse> listar() {
        log.info("Listando todas las habitaciones");
        return  habitacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(habitacionMapper::entidadAResponse).toList();

    }
    @Override
    public HabitacionResponse obtenerPorId(Long id) {
        log.info("Buscando habitacion activa con id {}", id);
        Habitacion habitacion = habitacionRepository.findByNumHabitacionAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Habitacion activa no encontrada con id: " + id));
        return habitacionMapper.entidadAResponse(habitacion);
    }

    private Habitacion obtenerEntidadPorId(Long id) {
        log.info("Buscando habitacion activa con id {}", id);
        return habitacionRepository.findByNumHabitacionAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Habitacion activa no encontrada con id: " + id));
    }



    @Override
    public void actualizarTipoHabitacion(Long numHabitacion, Long idTipo) {
        Habitacion habitacion = obtenerEntidadPorId(numHabitacion);
        log.info("Actualizando tipo de habitacion con id : {}", numHabitacion);

        TipoHabitacion nuevoTipo = TipoHabitacion.obtenerTipoPorCodigo(idTipo);
        habitacion.actualizarTipoHabitacion(nuevoTipo);

        habitacionRepository.save(habitacion);
    }

    @Override
    public void actualizarEstado(Long id, Long idEstado) {
        Habitacion habitacion = obtenerEntidadPorId(id);
        log.info("Actualizando estado de habitacion con id : {}", id);

        EstadoHabitacion nuevoEstado = EstadoHabitacion.obtenerEstadoPorCodigo(idEstado);
        habitacion.cambiarEstado(nuevoEstado);

        habitacionRepository.save(habitacion);
    }

    @Override
    public void liberar(Long id) {
        Habitacion habitacion = obtenerEntidadPorId(id);
        log.info("Liberando habitacion con id : {}", id);
        habitacion.liberar();
        habitacionRepository.save(habitacion);
    }





    @Override
    public HabitacionResponse registrar(HabitacionRequest request) {
        log.info("Registrando nueva habitacion con numero: {}", request.numHabitacion());

        habitacionRepository.findByNumHabitacionAndEstadoRegistro(request.numHabitacion(), EstadoRegistro.ACTIVO)
                .ifPresent(h -> {
                    throw new IllegalArgumentException("Ya existe una habitacion activa con el numero: " + request.numHabitacion());
                });

        Habitacion habitacion = habitacionMapper.requestAEntidad(request);
        habitacionRepository.save(habitacion);

        return habitacionMapper.entidadAResponse(habitacion);
    }

    @Override
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        log.info("Actualizando habitacion con id: {}", id);

        Habitacion habitacion = obtenerEntidadPorId(id);

        TipoHabitacion tipo = TipoHabitacion.valueOf(request.tipo().toUpperCase());
        habitacion.actualizar(tipo, request.precio(), request.capacidad());

        habitacionRepository.save(habitacion);

        return habitacionMapper.entidadAResponse(habitacion);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando habitacion con id: {}", id);

        Habitacion habitacion = obtenerEntidadPorId(id);
        habitacion.eliminar();

        habitacionRepository.save(habitacion);
    }

    @Override
    public HabitacionResponse obtenerPorNumeroHabitacion(Long numHabitacion) {
        log.info("Buscando habitacion activa con numero: {}", numHabitacion);
        Habitacion habitacion = habitacionRepository.findByNumHabitacionAndEstadoRegistro(numHabitacion, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("Habitacion activa no encontrada con numero: " + numHabitacion));
        return habitacionMapper.entidadAResponse(habitacion);
    }
}
