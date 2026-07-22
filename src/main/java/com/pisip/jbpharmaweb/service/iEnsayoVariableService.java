package com.pisip.jbpharmaweb.service;
import java.util.List; import java.util.Optional;
import com.pisip.jbpharmaweb.model.dto.request.EnsayoVariableRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoVariableResponseDto;
public interface iEnsayoVariableService {
 List<EnsayoVariableResponseDto> listar(); Optional<EnsayoVariableResponseDto> buscarPorId(long id); EnsayoVariableResponseDto guardar(EnsayoVariableRequestDto dto); EnsayoVariableResponseDto actualizar(long id,EnsayoVariableRequestDto dto); void eliminar(long id);
}
