package com.pisip.jbpharmaweb.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pisip.jbpharmaweb.model.dto.request.PlanProduccionRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.PlanProduccionResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.UsuarioResponseDTO;
import com.pisip.jbpharmaweb.service.IPlanProduccionService;
import com.pisip.jbpharmaweb.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/planproduccion")
public class PlanProduccionController {

	@Autowired
	private IPlanProduccionService servicioAPI;
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping
	public String leerpagina(Model model, HttpSession session) {
		List<PlanProduccionResponseDto> datosAPI = servicioAPI.listarPlan();
		List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios(); 

		Map<Integer, String> mapaUsuarios = usuarios.stream()
				.filter(u -> u.getIdUsuario() > 0 && u.getNombre() != null)
				.collect(Collectors.toMap(
						UsuarioResponseDTO::getIdUsuario, 
						UsuarioResponseDTO::getNombre,
						(existente, reemplazo) -> existente
				));

		Integer idUsuarioSession = (Integer) session.getAttribute("idUsuario");
		String nombreUsuarioSession = (String) session.getAttribute("nombreUsuario");

		if (datosAPI != null) {
			for (PlanProduccionResponseDto plan : datosAPI) {
				Integer idUser = plan.getIdUsuario();
				
				String nombreReal = (idUser != null) ? mapaUsuarios.get(idUser) : null;
				
				if (nombreReal != null) {
					plan.setNombreUsuario(nombreReal);
				} else if (plan.getNombreUsuario() == null || plan.getNombreUsuario().trim().isEmpty()) {
					plan.setNombreUsuario("Sin asignar");
				}
			}
		}

		model.addAttribute("listaplan", datosAPI);
		model.addAttribute("nombreUsuarioLogueado", nombreUsuarioSession);
		model.addAttribute("idUsuarioLogueado", idUsuarioSession);

		return "planproduccion/listarplan";
	}

	@GetMapping("/crearplan")
	public String mostrarFormularioCrear(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
	    List<PlanProduccionResponseDto> planesExistentes = servicioAPI.listarPlan();

	    if (planesExistentes != null && planesExistentes.stream()
	            .anyMatch(p -> p.getEstado() != null && p.getEstado().equalsIgnoreCase("EN PROCESO"))) {
	        redirectAttributes.addFlashAttribute("error", "No es posible crear un nuevo plan. Existe un plan de producción actualmente 'EN PROCESO'.");
	        return "redirect:/planproduccion";
	    }

	    PlanProduccionRequestDto dto = new PlanProduccionRequestDto();
	    Integer idUsuarioSession = (Integer) session.getAttribute("idUsuario");
	    String nombreUsuarioSession = (String) session.getAttribute("nombreUsuario");

	    if (idUsuarioSession != null) {
	        dto.setIdUsuario(idUsuarioSession);
	    }

	    dto.setEstado("EN PROCESO");

	    model.addAttribute("plan", dto);
	    model.addAttribute("nombreUsuarioLogueado", nombreUsuarioSession);
	    model.addAttribute("listausuarios", usuarioService.listarUsuarios());

	    return "planproduccion/crearplan";
	}

	@PostMapping("/guardar")
	public String guardarPlan(@ModelAttribute("plan") PlanProduccionRequestDto requestDto, 
	                          HttpSession session,
	                          Model model,
	                          RedirectAttributes redirectAttributes) {
	    try {
	        if (requestDto.getIdUsuario() == null) {
	            Integer idUsuarioSession = (Integer) session.getAttribute("idUsuario");
	            requestDto.setIdUsuario(idUsuarioSession);
	        }

	        if (requestDto.getEstado() == null || requestDto.getEstado().trim().isEmpty()) {
	            requestDto.setEstado("EN PROCESO");
	        }

	        List<PlanProduccionResponseDto> planesExistentes = servicioAPI.listarPlan();

	        if (planesExistentes != null) {
	            boolean existePlanEnProceso = planesExistentes.stream()
	                    .anyMatch(p -> p.getEstado() != null 
	                            && p.getEstado().equalsIgnoreCase("EN PROCESO"));

	            if (existePlanEnProceso) {
	                String nombreUsuarioSession = (String) session.getAttribute("nombreUsuario");
	                
	                model.addAttribute("error", "No se puede crear un nuevo plan de producción mientras exista uno con estado 'EN PROCESO'. Debe editar el plan activo a 'COMPLETADO' para poder continuar.");
	                model.addAttribute("nombreUsuarioLogueado", nombreUsuarioSession);
	                model.addAttribute("listausuarios", usuarioService.listarUsuarios());
	                
	                return "planproduccion/crearplan";
	            }

	            boolean yaExisteCodigo = planesExistentes.stream()
	                    .anyMatch(p -> p.getCodigoPlan() != null 
	                            && p.getCodigoPlan().equalsIgnoreCase(requestDto.getCodigoPlan()));

	            if (yaExisteCodigo) {
	                String nombreUsuarioSession = (String) session.getAttribute("nombreUsuario");
	                
	                model.addAttribute("error", "Ya existe un plan de producción registrado para el mes y año seleccionados (" + requestDto.getCodigoPlan() + ").");
	                model.addAttribute("nombreUsuarioLogueado", nombreUsuarioSession);
	                model.addAttribute("listausuarios", usuarioService.listarUsuarios());
	                
	                return "planproduccion/crearplan";
	            }
	        }

	        servicioAPI.guardarPlan(requestDto);
	        redirectAttributes.addFlashAttribute("success", "Registro guardado correctamente.");
	        
	        return "redirect:/planproduccion";
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Error al guardar el plan de producción: " + e.getMessage());
	        return "redirect:/planproduccion/crearplan";
	    }
	}
	
	@GetMapping("/editarplan")
	public String mostrarFormularioEditar(@RequestParam("idPlan") Integer idPlan, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		PlanProduccionResponseDto plan = servicioAPI.buscarPorId(idPlan);
		Integer idUsuarioSession = (Integer) session.getAttribute("idUsuario");

		if (plan.getIdUsuario() != null && !plan.getIdUsuario().equals(idUsuarioSession)) {
			redirectAttributes.addFlashAttribute("error", "No tienes permisos para modificar este plan.");
			return "redirect:/planproduccion";
		}

		PlanProduccionRequestDto dto = new PlanProduccionRequestDto();
		dto.setIdPlan(plan.getIdPlan());
		dto.setCodigoPlan(plan.getCodigoPlan());
		dto.setAnio(plan.getAnio());
		dto.setMes(plan.getMes());
		dto.setFechaEmision(plan.getFechaEmision());
		dto.setEstado(plan.getEstado());
		dto.setDescripcion(plan.getDescripcion());
		dto.setCantidadLotesEstimada(plan.getCantidadLotesEstimada());
		dto.setIdUsuario(plan.getIdUsuario());

		model.addAttribute("plan", dto);
		model.addAttribute("listausuarios", usuarioService.listarUsuarios());

		return "planproduccion/editarplan";
	}

	@PostMapping("/eliminar/{id}")
	public String eliminarPlan(@PathVariable("id") Integer id, HttpSession session, RedirectAttributes redirectAttributes) {
		try {
			PlanProduccionResponseDto plan = servicioAPI.buscarPorId(id);
			Integer idUsuarioSession = (Integer) session.getAttribute("idUsuario");

			if (plan.getIdUsuario() != null && !plan.getIdUsuario().equals(idUsuarioSession)) {
				redirectAttributes.addFlashAttribute("error", "No tienes permisos para eliminar este plan.");
				return "redirect:/planproduccion";
			}

			servicioAPI.eliminarPlan(id);
			redirectAttributes.addFlashAttribute("success", "Plan de producción eliminado correctamente.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el plan de producción.");
		}
		return "redirect:/planproduccion";
	}

	@PostMapping("/actualizar")
	public String actualizarPlan(@ModelAttribute("plan") PlanProduccionRequestDto dto,
	                             HttpSession session,
	                             RedirectAttributes redirectAttributes) {
	    try {
	        if (dto.getIdUsuario() == null) {
	            Integer idUsuarioSession = (Integer) session.getAttribute("idUsuario");
	            dto.setIdUsuario(idUsuarioSession);
	        }

	        servicioAPI.actualizarPlan(dto.getIdPlan(), dto);
	        redirectAttributes.addFlashAttribute("success", "Plan de producción actualizado correctamente.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Error al actualizar el plan de producción: " + e.getMessage());
	    }
	    return "redirect:/planproduccion";
	}
}