package com.reservashoteleras.huespedes.entity;


import com.daniel.commons.enums.EstadoRegistro;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HUESPEDES")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Builder
public class Huesped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HUESPED", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "APELLIDO_PATERNO", nullable = false)
    private String apellidoPaterno;

    @Column(name = "APELLIDO_MATERNO", nullable = false)
    private String apellidoMaterno;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "TELEFONO", nullable = false, unique = true)
    private String telefono;

    @Column(name = "TIPO_DOCUMENTO", nullable = false)
    private String tipoDocumento;

    @Column(name = "DOCUMENTO", nullable = false, unique = true)
    private String documento;

    @Column(name = "NACIONALIDAD", nullable = false)
    private String nacionalidad;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO", nullable = false)
    private EstadoRegistro estado = EstadoRegistro.ACTIVO;

    public void eliminar() {
        this.estado = EstadoRegistro.ELIMINADO;
    }

    public void actualizar(String nombre, String apellidoPaterno, String apellidoMaterno, String email,
                           String telefono, String tipoDocumento, String documento, String nacionalidad) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.telefono = telefono;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.nacionalidad = nacionalidad;
    }
}
