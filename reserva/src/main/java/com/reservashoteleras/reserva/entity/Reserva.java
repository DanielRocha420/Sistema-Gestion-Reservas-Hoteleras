package com.reservashoteleras.reserva.entity;

import com.daniel.commons.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Entity
@Table()
@AllArgsConstructor
@NoArgsConstructor
@Getter@Builder
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RESERVA", nullable = false)
    private Long id;

    @Column(name = "ESTADO_RESERVA", nullable = false)
    private EstadoReserva estado;

    @Column(name = "FECHA_ENTRADA", nullable = false)
    private LocalDate fecha_Entrada;

    @Column(name = "FECHA_SALIDA", nullable = false)
    private LocalDate fecha_Salida;

}
