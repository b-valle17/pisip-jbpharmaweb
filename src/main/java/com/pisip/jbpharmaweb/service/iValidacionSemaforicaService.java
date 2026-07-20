package com.pisip.jbpharmaweb.service;
import java.util.List; import java.util.Optional;
import com.pisip.jbpharmaweb.model.dto.request.ValidacionSemaforicaRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.ValidacionSemaforicaResponseDto;
public interface iValidacionSemaforicaService {
 List<ValidacionSemaforicaResponseDto> listar(); Optional<ValidacionSemaforicaResponseDto> buscarPorId(long id); ValidacionSemaforicaResponseDto guardar(ValidacionSemaforicaRequestDto dto); ValidacionSemaforicaResponseDto actualizar(long id,ValidacionSemaforicaRequestDto dto); void eliminar(long id);
}
