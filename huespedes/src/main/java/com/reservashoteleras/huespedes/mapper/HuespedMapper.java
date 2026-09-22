package com.reservashoteleras.huespedes.mapper;

import com.daniel.commons.dto.huesped.HuespedRequest;
import com.daniel.commons.dto.huesped.HuespedResponse;
import com.daniel.commons.mapper.CommonMapper;
import com.reservashoteleras.huespedes.entity.Huesped;
import org.springframework.stereotype.Component;

@Component
public class HuespedMapper implements CommonMapper<HuespedRequest, HuespedResponse, Huesped> {
    @Override
    public Huesped requestAEntidad(HuespedRequest request) {
        if (request == null) return null;
        return Huesped.builder()
                .nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .email(request.email())
                .telefono(request.telefono())
                .tipoDocumento(request.tipoDocumento())
                .documento(request.documento())
                .nacionalidad(request.nacionalidad())
                .build();
    }

    @Override
    public HuespedResponse entidadAResponse(Huesped entidad) {
        if (entidad == null) return null;
        return new HuespedResponse(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getApellidoPaterno(),
                entidad.getApellidoMaterno(),
                entidad.getEmail(),
                entidad.getTelefono(),
                entidad.getTipoDocumento(),
                entidad.getDocumento(),
                entidad.getNacionalidad(),
                entidad.getEstado()
        );
    }
}
