package com.reservashoteleras.huespedes.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HUESPEDES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Huesped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HUESPED", nullable = true)
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO_PATERNO")
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO")
    private String apellidoMaterno;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "TELEFONO")
    private String telefono;

    //@Column(name = "DOCUMENTACION")
    //private Documentacion documento;

    @Column(name = "NACIONALIDAD")
    private String nacionalidad;
}
