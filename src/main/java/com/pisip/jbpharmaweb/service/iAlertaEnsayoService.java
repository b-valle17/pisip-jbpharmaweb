package com.pisip.jbpharmaweb.service;
import java.util.List; import java.util.Optional;
import com.pisip.jbpharmaweb.model.dto.request.AlertaEnsayoRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.AlertaEnsayoResponseDto;
public interface iAlertaEnsayoService {
 List<AlertaEnsayoResponseDto> listar(); Optional<AlertaEnsayoResponseDto> buscarPorId(long id); AlertaEnsayoResponseDto guardar(AlertaEnsayoRequestDto dto); AlertaEnsayoResponseDto actualizar(long id,AlertaEnsayoRequestDto dto); void eliminar(long id);
}
