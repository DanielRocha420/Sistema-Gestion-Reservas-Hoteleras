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

        return huespedesRepository.findByEstado(EstadoRegistro.ACTIVO).stream()
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
        validarUnicidad(request, null);
        Huesped huesped = huespedMapper.requestAEntidad(request);
        return huespedMapper.entidadAResponse(huespedesRepository.save(huesped));
    }

    @Override
    public HuespedResponse actualizar(HuespedRequest request, Long id) {
        log.info("Iniciando actualizar del huesped");
        Huesped huesped = buscarHuespedActivo(id);
        validarUnicidad(request, id);
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

    private Huesped buscarHuespedActivo(Long id) {
        Huesped huesped = buscarHuesped(id);
        if (huesped.getEstado() != EstadoRegistro.ACTIVO) {
            throw new RecursoNoEncontradoException("Huesped no encontrado con id " + id);
        }
        return huesped;
    }

    private void validarUnicidad(HuespedRequest request, Long id) {
        validarCampo("email", request.email(), ocupadoEmail(request.email(), id));
        validarCampo("telefono", request.telefono(), ocupadoTelefono(request.telefono(), id));
        validarCampo("documento", request.documento(), ocupadoDocumento(request.documento(), id));
    }

    private void validarCampo(String campo, String valor, boolean ocupado) {
        if (ocupado) {
            throw new IllegalStateException("Ya existe un huesped con el " + campo + " " + valor);
        }
    }

    private boolean ocupadoEmail(String email, Long id) {
        return id == null
                ? huespedesRepository.existsByEmail(email)
                : huespedesRepository.existsByEmailAndIdNot(email, id);
    }

    private boolean ocupadoTelefono(String telefono, Long id) {
        return id == null
                ? huespedesRepository.existsByTelefono(telefono)
                : huespedesRepository.existsByTelefonoAndIdNot(telefono, id);
    }

    private boolean ocupadoDocumento(String documento, Long id) {
        return id == null
                ? huespedesRepository.existsByDocumento(documento)
                : huespedesRepository.existsByDocumentoAndIdNot(documento, id);
    }
}
