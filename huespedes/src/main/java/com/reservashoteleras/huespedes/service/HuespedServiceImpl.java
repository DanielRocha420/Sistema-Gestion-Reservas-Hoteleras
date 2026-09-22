package com.reservashoteleras.huespedes.service;

import com.daniel.commons.dto.huesped.HuespedRequest;
import com.daniel.commons.dto.huesped.HuespedResponse;
import com.daniel.commons.enums.EstadoRegistro;
import com.daniel.commons.exceptions.RecursoNoEncontradoException;
import com.reservashoteleras.huespedes.entity.Huesped;
import com.reservashoteleras.huespedes.mapper.HuespedMapper;
import com.reservashoteleras.huespedes.repository.HuespedesRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Transactional
@Service
public class HuespedServiceImpl implements HuespedService {

    private final HuespedesRepository huespedesRepository;
    private final HuespedMapper huespedMapper;


    @Override
    @Transactional()
    public List<HuespedResponse> listar() {
        log.info("Iniciando lista de huespedes");

        return huespedesRepository.findAll().stream()
                .map(huespedMapper::entidadAResponse)
                .toList();
    }

    @Override
    @Transactional
    public HuespedResponse obtenerPorId(Long id) {
        log.info("Iniciando obtenerPorId del huesped");
        Huesped huesped = buscarHuesped(id);
        return huespedMapper.entidadAResponse(huesped);
    }

    @Override
    public HuespedResponse registrar(HuespedRequest request) {
        log.info("Iniciando registro del huesped");

        if (huespedesRepository.existsByEmail(request.email())) {
            throw new IllegalStateException("Ya existe un huesped con el email " + request.email());
        }
        if (huespedesRepository.existsByTelefono(request.telefono())) {
            throw new IllegalStateException("Ya existe un huesped con el telefono " + request.telefono());
        }
        if (huespedesRepository.existsByDocumento(request.documento())) {
            throw new IllegalStateException("Ya existe un huesped con el documento " + request.documento());
        }
        Huesped huesped = huespedMapper.requestAEntidad(request);
        return huespedMapper.entidadAResponse(huespedesRepository.save(huesped));
    }

    @Override
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        log.info("Iniciando actualizar del huesped");

        Huesped huesped = buscarHuesped(id);
        if (huesped.getEstado() != EstadoRegistro.ACTIVO) {
            throw new RecursoNoEncontradoException("Huesped no encontrado con id " + id);
        }
        if (huespedesRepository.existsByEmailAndIdNot(request.email(), id)) {
            throw new IllegalStateException("Ya existe un huesped con el email " + request.email());
        }
        if (huespedesRepository.existsByTelefonoAndIdNot(request.telefono(), id)) {
            throw new IllegalStateException("Ya existe un huesped con el telefono " + request.telefono());
        }
        if (huespedesRepository.existsByDocumentoAndIdNot(request.documento(), id)) {
            throw new IllegalStateException("Ya existe un huesped con el documento " + request.documento());
        }
        huesped.actualizar(
                request.nombre().trim(),
                request.apellidoPaterno().trim(),
                request.apellidoMaterno().trim(),
                request.email(),
                request.telefono(),
                request.tipoDocumento(),
                request.documento(),
                request.nacionalidad()
        );
        return huespedMapper.entidadAResponse(huespedesRepository.save(huesped));
    }

    @Override
    public void eliminar(Long id) {
        log.info("Iniciando eliminar el huesped");

        Huesped huesped = buscarHuesped(id);
        huesped.eliminar();
        huespedesRepository.save(huesped);
    }

    private Huesped buscarHuesped(Long id) {
        return huespedesRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("Huesped no encontrado con id "+ id));
    }
}
