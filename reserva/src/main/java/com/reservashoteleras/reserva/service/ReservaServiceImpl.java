package com.reservashoteleras.reserva.service;

import com.daniel.commons.client.HabitacionClient;
import com.daniel.commons.dto.habitacion.HabitacionResponse;
import com.daniel.commons.dto.huesped.HuespedResponse;
import com.daniel.commons.dto.reserva.ReservaRequest;
import com.daniel.commons.dto.reserva.ReservaResponse;
import com.daniel.commons.enums.EstadoHabitacion;
import com.daniel.commons.enums.EstadoRegistro;
import com.daniel.commons.enums.EstadoReserva;
import com.daniel.commons.exceptions.RecursoNoEncontradoException;
import com.reservashoteleras.reserva.entity.Reserva;
import com.reservashoteleras.reserva.mapper.ReservaMapper;
import com.daniel.commons.client.HuespedClient;
import com.reservashoteleras.reserva.repository.ReservaRepository;
import lombok.extern.slf4j.Slf4j;
import feign.FeignException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        return reservaRepository.findAll().stream()
                .filter(reserva -> reserva.getEstadoRegistro() == EstadoRegistro.ACTIVO)
                .map(reservaMapper::entidadAResponse)
                .toList();
    }

    @Override
    public ReservaResponse obtenerPorId(Long id) {
        log.info("Iniciando obtenerPorId del reserva");
        Reserva reserva = buscarReserva(id);
        return null;
    }

    @Override
    @Transactional
    public ReservaResponse registrar(ReservaRequest request) {
        log.info("Iniciando registrar de la reserva para el huesped {} y la habitacion {}",
                request.idHuesped(), request.numHabitacion());

        validarFechas(request);
        validarHuespedActivo(request.idHuesped());
        validarHabitacionDisponible(request.numHabitacion());

        Reserva reserva = reservaMapper.requestAEntidad(request);
        reserva.setEstado(EstadoReserva.CONFIRMADA);
        reserva.setNumHabitacion(request.numHabitacion());
        reserva = reservaRepository.save(reserva);
        habitacionClient.actualizarEstado(request.numHabitacion(), EstadoHabitacion.OCUPADA.getCodigo());
        return reservaMapper.entidadAResponse(reserva);
    }

    @Override
    @Transactional
    public ReservaResponse actualizar(ReservaRequest request, Long id) {
        log.info("Actualizando la reserva {}", id);
        Reserva reserva = buscarReserva(id);

        validarFechas(request);
        actualizarFechasSegunEstado(reserva, request);
        actualizarHabitacionSegunEstado(reserva, request);

        return reservaMapper.entidadAResponse(reservaRepository.save(reserva));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        log.info("Iniciando eliminar lógicamente la reserva {}", id);
        Reserva reserva = buscarReserva(id);
        reserva.eliminar();
        reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public ReservaResponse checkIn(Long id, Long numHabitacion) {
        log.info("Check-in de la reserva {} en la habitacion {}", id, numHabitacion);
        Reserva reserva = buscarReserva(id);
        
        validarHabitacionOcupada(numHabitacion);
        reserva.checkIn();
        
        return reservaMapper.entidadAResponse(reservaRepository.save(reserva));
    }

    @Override
    @Transactional
    public ReservaResponse checkOut(Long id, Long numHabitacion) {
        log.info("Check-out de la reserva {} en la habitacion {}", id, numHabitacion);
        Reserva reserva = buscarReserva(id);
        
        obtenerHabitacionActiva(numHabitacion);
        reserva.checkOut();
        Reserva guardada = reservaRepository.save(reserva);
        habitacionClient.liberar(numHabitacion);
        
        return reservaMapper.entidadAResponse(guardada);
    }

    @Override
    @Transactional
    public ReservaResponse cancelar(Long id, Long numHabitacion) {
        log.info("Cancelando la reserva {} de la habitacion {}", id, numHabitacion);
        Reserva reserva = buscarReserva(id);
        
        obtenerHabitacionActiva(numHabitacion);
        reserva.cancelar();
        Reserva guardada = reservaRepository.save(reserva);
        habitacionClient.liberar(numHabitacion);
        
        return reservaMapper.entidadAResponse(guardada);
    }

    private void actualizarFechasSegunEstado(Reserva reserva, ReservaRequest request) {
        LocalDateTime nuevaFechaEntrada = request.fechaEntrada();
        LocalDateTime nuevaFechaSalida = request.fechaSalida();

        if (nuevaFechaEntrada != null && nuevaFechaSalida != null) {
            reserva.cambiarFechas(nuevaFechaEntrada, nuevaFechaSalida);
        } else if (nuevaFechaEntrada != null) {
            reserva.cambiarFechaEntrada(nuevaFechaEntrada);
        } else if (nuevaFechaSalida != null) {
            reserva.cambiarFechaSalida(nuevaFechaSalida);
        }
    }

    private void actualizarHabitacionSegunEstado(Reserva reserva, ReservaRequest request) {
        Long nuevaHabitacion = request.numHabitacion();
        if (nuevaHabitacion != null && !nuevaHabitacion.equals(reserva.getNumHabitacion())) {
            Long habitacionAnterior = reserva.getNumHabitacion();
            reserva.cambiarHabitacion(nuevaHabitacion);

            // Liberar la habitación anterior
            habitacionClient.liberar(habitacionAnterior);

            // Ocupar la nueva habitación
            habitacionClient.actualizarEstado(nuevaHabitacion, EstadoHabitacion.OCUPADA.getCodigo());
        }
    }

    private void validarFechas(ReservaRequest request) {
        if (request.fechaEntrada() == null || request.fechaSalida() == null
                || !request.fechaSalida().toLocalDate().isAfter(request.fechaEntrada().toLocalDate())) {
            throw new IllegalArgumentException("La fecha de entrada debe ser anterior a la fecha de salida");
        }
    }

    private void validarHuespedActivo(Long idHuesped) {
        HuespedResponse huesped = obtenerHuesped(idHuesped);
        if (huesped.estado() != EstadoRegistro.ACTIVO) {
            throw new IllegalArgumentException("El huesped debe existir y estar ACTIVO");
        }
    }

    private void validarHabitacionDisponible(Long numHabitacion) {
        HabitacionResponse habitacion = obtenerHabitacion(numHabitacion);
        if (!EstadoHabitacion.DISPONIBLE.name().equals(habitacion.estado())) {
            throw new IllegalArgumentException("La habitacion debe estar DISPONIBLE");
        }
    }

    private void validarHabitacionOcupada(Long numHabitacion) {
        HabitacionResponse habitacion = obtenerHabitacion(numHabitacion);
        if (!EstadoHabitacion.OCUPADA.name().equals(habitacion.estado())) {
            throw new IllegalArgumentException("La habitacion debe estar OCUPADA");
        }
    }

    private HuespedResponse obtenerHuesped(Long idHuesped) {
        try {
            return huespedClient.obtenerPorId(idHuesped);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("El huesped debe existir y estar ACTIVO");
        }
    }

    private HabitacionResponse obtenerHabitacion(Long numHabitacion) {
        try {
            return habitacionClient.obtenerPorId(numHabitacion);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("La habitacion debe existir y estar ACTIVA");
        }
    }

    private HabitacionResponse obtenerHabitacionActiva(Long numHabitacion) {
        try {
            return habitacionClient.obtenerPorId(numHabitacion);
        } catch (FeignException.NotFound e) {
            throw new RecursoNoEncontradoException("La habitacion debe existir y estar ACTIVA");
        }
    }

    private Reserva buscarReserva(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La reserva no existe con el id " + id));
    }
}


