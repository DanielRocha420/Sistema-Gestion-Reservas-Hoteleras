package com.reservashoteleras.huespedes.repository;

import com.reservashoteleras.huespedes.entity.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HuespedesRepository extends JpaRepository<Huesped, Long> {

    boolean existsByEmail(String email);

    boolean existsByTelefono(String telefono);

    boolean existsByDocumento(String documento);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByTelefonoAndIdNot(String telefono, Long id);

    boolean existsByDocumentoAndIdNot(String documento, Long id);
}
