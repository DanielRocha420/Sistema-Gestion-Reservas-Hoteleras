package com.reservashoteleras.huespedes.repository;

import com.daniel.commons.enums.EstadoRegistro;
import com.reservashoteleras.huespedes.entity.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HuespedesRepository extends JpaRepository<Huesped, Long> {

    List<Huesped> findByEstado(EstadoRegistro estado);

    boolean existsByEmail(String email);

    boolean existsByTelefono(String telefono);

    boolean existsByDocumento(String documento);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByTelefonoAndIdNot(String telefono, Long id);

    boolean existsByDocumentoAndIdNot(String documento, Long id);
}
