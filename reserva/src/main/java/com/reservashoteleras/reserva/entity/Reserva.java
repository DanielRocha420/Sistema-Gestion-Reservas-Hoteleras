package com.reservashoteleras.reserva.entity;

import com.daniel.commons.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVA")
@AllArgsConstructor
@NoArgsConstructor
@Getter@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_RESERVA", nullable = false)
    private EstadoReserva estado;

    @Column(name = "FECHA_ENTRADA", nullable = false)
    private LocalDateTime fecha_Entrada;

    @Column(name = "FECHA_SALIDA", nullable = false)
    private LocalDateTime fecha_Salida;

    public void cambiarEstado(EstadoReserva nuevoEstado) {
        this.estado = nuevoEstado;
    }

    @PrePersist
    @PreUpdate
    private void validarFechas() {
        if (fecha_Entrada == null || fecha_Salida == null
                || !fecha_Salida.toLocalDate().isAfter(fecha_Entrada.toLocalDate())) {
            throw new IllegalArgumentException("La fecha de entrada debe ser anterior a la fecha de salida");
        }
    }

}
