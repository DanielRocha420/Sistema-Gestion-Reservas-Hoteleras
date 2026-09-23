package com.reservashoteleras.reserva.service;

import com.daniel.commons.dto.habitacion.HabitacionResponse;
import com.daniel.commons.dto.huesped.HuespedResponse;
import com.daniel.commons.dto.reserva.ReservaRequest;
import com.daniel.commons.dto.reserva.ReservaResponse;
import com.daniel.commons.enums.EstadoHabitacion;
import com.daniel.commons.enums.EstadoRegistro;
import com.daniel.commons.enums.EstadoReserva;
import com.daniel.commons.exceptions.RecursoNoEncontradoException;
import com.reservashoteleras.reserva.client.HabitacionClient;
import com.reservashoteleras.reserva.client.HuespedClient;
import com.reservashoteleras.reserva.entity.Reserva;
import com.reservashoteleras.reserva.mapper.ReservaMapper;
import com.reservashoteleras.reserva.repository.ReservaRepository;
import lombok.extern.slf4j.Slf4j;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReservaServiceImpl implements ReservaService{

    private final ReservaRepository reservaRepository;
    private final ReservaMapper reservaMapper;
    private final HabitacionClient habitacionClient;
    private final HuespedClient huespedClient;

    public ReservaServiceImpl(ReservaRepository reservaRepository,
                              ReservaMapper reservaMapper,
                              HabitacionClient habitacionClient,
                              HuespedClient huespedClient) {
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
        this.habitacionClient = habitacionClient;
        this.huespedClient = huespedClient;
    }
    @Override
    public List<ReservaResponse> listar() {
        log.info("Iniciando listar Reservas");
        return List.of();
    }

    @Override
    public ReservaResponse obtenerPorId(Long id) {
        log.info("Iniciando obtenerPorId del reserva");
        Reserva reserva = buscarReserva(id);
        return null;
    }

    @Override
    public ReservaResponse registrar(ReservaRequest request) {
        log.info("Iniciando registrar de la reserva para el huesped {} y la habitacion {}",
                request.idHuesped(), request.idHabitacion());

        validarFechas(request);
        validarHuespedActivo(request.idHuesped());
        validarHabitacionDisponible(request.idHabitacion());

        Reserva reserva = reservaRepository.save(reservaMapper.requestAEntidad(request));
        habitacionClient.actualizarEstado(request.idHabitacion(), EstadoHabitacion.OCUPADA.getCodigo());
        return reservaMapper.entidadAResponse(reserva);
    }

    private void validarFechas(ReservaRequest request) {
        if (request.fechaEntrada() == null || request.fechaSalida() == null
                || !request.fechaSalida().toLocalDate().isAfter(request.fechaEntrada().toLocalDate())) {
            throw new IllegalArgumentException("La fecha de entrada debe ser anterior a la fecha de salida");
        }
    }

    private void validarHuespedActivo(Long idHuesped) {
        HuespedResponse huesped;
        try {
            huesped = huespedClient.obtenerPorId(idHuesped);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("El huesped debe existir y estar ACTIVO");
        }
        if (huesped.estado() != EstadoRegistro.ACTIVO) {
            throw new IllegalArgumentException("El huesped debe existir y estar ACTIVO");
        }
    }

    private void validarHabitacionDisponible(Long idHabitacion) {
        HabitacionResponse habitacion;
        try {
            habitacion = habitacionClient.obtenerPorId(idHabitacion);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("La habitacion debe existir y estar ACTIVA");
        }
        if (!EstadoHabitacion.DISPONIBLE.name().equals(habitacion.estado())) {
            throw new IllegalArgumentException("La habitacion debe estar DISPONIBLE");
        }
    }

    @Override
    public ReservaResponse actualizar(ReservaRequest request, Long id) {
        log.info("Iniciando actualizar del huesped");
        validarFechas(request);
        Reserva reserva = buscarReserva(id);
        return null;
    }

    @Override
    public void eliminar(Long id) {
        log.info("Iniciando eliminar el huesped");
        Reserva reserva = buscarReserva(id);
        reservaRepository.delete(reserva);
    }

    private Reserva buscarReserva(Long id) {
        return reservaRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("La reserva no existe con el id " + id));
    }

    @Override
    public ReservaResponse checkIn(Long id, Long idHabitacion) {
        log.info("Check-in de la reserva {} en la habitacion {}", id, idHabitacion);
        Reserva reserva = buscarReserva(id);
        if (reserva.getEstado() != EstadoReserva.CONFIRMADA) {
            throw new IllegalArgumentException("El check-in solo se permite cuando la reserva esta CONFIRMADA");
        }
        HabitacionResponse habitacion = obtenerHabitacionActiva(idHabitacion);
        if (!EstadoHabitacion.OCUPADA.name().equals(habitacion.estado())) {
            throw new IllegalArgumentException("La habitacion debe permanecer OCUPADA");
        }
        reserva.cambiarEstado(EstadoReserva.EN_CURSO);
        return reservaMapper.entidadAResponse(reservaRepository.save(reserva));
    }

    @Override
    public ReservaResponse checkOut(Long id, Long idHabitacion) {
        log.info("Check-out de la reserva {} en la habitacion {}", id, idHabitacion);
        Reserva reserva = buscarReserva(id);
        if (reserva.getEstado() != EstadoReserva.EN_CURSO) {
            throw new IllegalArgumentException("El check-out solo se permite cuando la reserva esta EN_CURSO");
        }
        obtenerHabitacionActiva(idHabitacion);
        reserva.cambiarEstado(EstadoReserva.FINALIZADA);
        Reserva guardada = reservaRepository.save(reserva);
        habitacionClient.liberar(idHabitacion);
        return reservaMapper.entidadAResponse(guardada);
    }

    @Override
    public ReservaResponse cancelar(Long id, Long idHabitacion) {
        log.info("Cancelando la reserva {} de la habitacion {}", id, idHabitacion);
        Reserva reserva = buscarReserva(id);
        if (reserva.getEstado() != EstadoReserva.CONFIRMADA) {
            throw new IllegalArgumentException("La cancelacion solo se permite cuando la reserva esta CONFIRMADA");
        }
        obtenerHabitacionActiva(idHabitacion);
        reserva.cambiarEstado(EstadoReserva.CANCELADA);
        Reserva guardada = reservaRepository.save(reserva);
        habitacionClient.liberar(idHabitacion);
        return reservaMapper.entidadAResponse(guardada);
    }

    private HabitacionResponse obtenerHabitacionActiva(Long idHabitacion) {
        try {
            return habitacionClient.obtenerPorId(idHabitacion);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("La habitacion debe existir y estar ACTIVA");
        }
    }

    @Override
    public boolean existsByHuespedId(Long id) {
        return false;
    }

    @Override
    public boolean existsByHabitacionId(Long id) {
        return false;
    }
}
