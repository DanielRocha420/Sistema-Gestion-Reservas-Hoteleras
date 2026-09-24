package com.reservashoteleras.huespedes.entity;


import com.daniel.commons.enums.EstadoRegistro;
import com.daniel.commons.utils.StringCustomUtils;
import com.daniel.commons.utils.ValoresNumericosUtils;
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

    /**
     * Restriccion: No se puede eliminar un huesped con reservas EN_CURSO
     */
    public void eliminar() {
        validarNoEliminado();
        this.estado = EstadoRegistro.ELIMINADO;
    }

    public void actualizar(String nombre, String apellidoPaterno, String apellidoMaterno, String email,
                           String telefono, String tipoDocumento, String documento, String nacionalidad) {

        validarNoEliminado();

        validadDatos(nombre, apellidoPaterno, apellidoMaterno, email, telefono);

        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.telefono = telefono;
        this.tipoDocumento = tipoDocumento;
        this.documento = documento;
        this.nacionalidad = nacionalidad;
    }

    /**
     *Validacion para el tamaño del campo
     */

    private void validadDatos(String nombre, String apellidoPaterno, String apellidoMaterno, String email, String telefono){

        StringCustomUtils.validarTamanio(nombre, 2, 50, "El nombre es requerido y debe tener entre 2 y 50 caracteres");
        StringCustomUtils.validarTamanio(apellidoPaterno, 2, 50, "El apellido paterno es requerido y debe tener entre 2 y 50 caracteres");
        StringCustomUtils.validarTamanio(apellidoMaterno, 2, 50, "El apellido materno es requerido y debe tener entre 2 y 50 caracteres");
        StringCustomUtils.validarTamanio(email, 2, 100, "El email es requerido y debe tener entre 1 y 100 caracteres");
        StringCustomUtils.validarTamanio(telefono, 10, 10, "El telefono es requerido y debe tener 10 dígitos");

    }

    /**
     * Validacion de que el huesped no esté eliminado
     */
    private void validarNoEliminado() {
        if (this.estado == EstadoRegistro.ELIMINADO) {
            throw new IllegalArgumentException("El huesped ya esta eliminado");
        }
    }
}
