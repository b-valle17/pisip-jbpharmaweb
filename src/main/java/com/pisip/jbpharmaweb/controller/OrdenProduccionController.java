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

import com.pisip.jbpharmaweb.model.dto.request.OrdenProduccionRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.OrdenProduccionResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.PlanProduccionResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.ProductoResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.UsuarioResponseDTO;
import com.pisip.jbpharmaweb.service.IOrdenProduccionService;
import com.pisip.jbpharmaweb.service.IPlanProduccionService;
import com.pisip.jbpharmaweb.service.IProductoService;
import com.pisip.jbpharmaweb.service.IUsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ordenproduccion")
public class OrdenProduccionController {

	@Autowired
	private IOrdenProduccionService servicioAPI;
	
	@Autowired
	private IPlanProduccionService planService; 

	@Autowired
	private IProductoService productoService; 

	@Autowired
	private IUsuarioService usuarioService;
	
	@GetMapping
	public String leerpagina(Model model, HttpSession session) {
		List<OrdenProduccionResponseDto> datosAPI = servicioAPI.listarOrden();
		
		List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios(); 
		List<PlanProduccionResponseDto> planes = planService.listarPlan();
		List<ProductoResponseDto> productos = productoService.listarProductos();

		Map<Integer, String> mapaUsuarios = usuarios.stream()
				.filter(u -> u.getIdUsuario() > 0 && u.getNombre() != null)
				.collect(Collectors.toMap(UsuarioResponseDTO::getIdUsuario, UsuarioResponseDTO::getNombre, (e, r) -> e));

		Map<Integer, String> mapaPlanes = planes.stream()
				.filter(p -> p.getIdPlan() != null && p.getCodigoPlan() != null)
				.collect(Collectors.toMap(PlanProduccionResponseDto::getIdPlan, PlanProduccionResponseDto::getCodigoPlan, (e, r) -> e));

		Map<Integer, String> mapaProductos = productos.stream()
				.filter(pr -> pr.getIdProducto() != null && pr.getNombreProducto() != null)
				.collect(Collectors.toMap(ProductoResponseDto::getIdProducto, ProductoResponseDto::getNombreProducto, (e, r) -> e));

		Integer idUsuarioSession = (Integer) session.getAttribute("idUsuario");
		String nombreUsuarioSession = (String) session.getAttribute("nombreUsuario");

		if (datosAPI != null) {
			for (OrdenProduccionResponseDto orden : datosAPI) {
				Integer idUser = orden.getIdUsuario();
				String nombreReal = (idUser != null) ? mapaUsuarios.get(idUser) : null;
				orden.setNombreUsuario(nombreReal != null ? nombreReal : "Sin asignar");

				Integer idPlan = orden.getIdPlan();
				String codPlan = (idPlan != null) ? mapaPlanes.get(idPlan) : null;
				orden.setCodigoPlan(codPlan != null ? codPlan : "Sin plan");

				Integer idProd = orden.getIdProducto();
				String nomProd = (idProd != null) ? mapaProductos.get(idProd) : null;
				orden.setNombreProducto(nomProd != null ? nomProd : "Sin producto");
			}
		}

		model.addAttribute("listaorden", datosAPI);
		model.addAttribute("nombreUsuarioLogueado", nombreUsuarioSession);
		model.addAttribute("idUsuarioLogueado", idUsuarioSession);

		return "ordenproduccion/listarorden"; 
	}

	@GetMapping("/crearorden")
	public String leerpaginacrear(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
	    List<OrdenProduccionResponseDto> ordenesExistentes = servicioAPI.listarOrden();

	    boolean existeOrdenEnProceso = ordenesExistentes != null && ordenesExistentes.stream()
	            .anyMatch(o -> o.getEstado() != null && "EN PROCESO".equalsIgnoreCase(o.getEstado().trim()));

	    if (existeOrdenEnProceso) {
	        redirectAttributes.addFlashAttribute("error", 
	            "No se puede crear una nueva orden de producción mientras exista una en estado 'EN PROCESO'. Debe ser cambiada a 'COMPLETADO' o 'CANCELADO'.");
	        return "redirect:/ordenproduccion";
	    }

	    OrdenProduccionRequestDto dto = new OrdenProduccionRequestDto();

	    Integer idUsuarioSession = (Integer) session.getAttribute("idUsuario");
	    String nombreUsuarioSession = (String) session.getAttribute("nombreUsuario");

	    if (idUsuarioSession != null) {
	        dto.setIdUsuario(idUsuarioSession);
	    }

	    int maximoActual = 0;
	    if (ordenesExistentes != null) {
	        for (OrdenProduccionResponseDto existente : ordenesExistentes) {
	            String numero = existente.getNumeroLote();
	            if (numero != null && numero.startsWith("LOT-")) {
	                try {
	                    maximoActual = Math.max(maximoActual, Integer.parseInt(numero.substring(4)));
	                } catch (NumberFormatException ex) {
	                }
	            }
	        }
	    }
	    dto.setNumeroLote(String.format("LOT-%03d", maximoActual + 1));
	    dto.setEstado("EN PROCESO");

	    List<PlanProduccionResponseDto> planes = planService.listarPlan();
	    List<ProductoResponseDto> productos = productoService.listarProductos(); 
	    List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios();

	    model.addAttribute("orden", dto);
	    model.addAttribute("nombreUsuarioLogueado", nombreUsuarioSession);
	    model.addAttribute("listaplan", planes);           
	    model.addAttribute("listaproductos", productos);  
	    model.addAttribute("listausuarios", usuarios);     

	    return "ordenproduccion/crearorden";
	}

	@PostMapping("/guardar")
	public String guardarOrden(@ModelAttribute("orden") OrdenProduccionRequestDto requestDto, 
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

	        List<OrdenProduccionResponseDto> ordenesExistentes = servicioAPI.listarOrden();
	        boolean existeOrdenEnProceso = ordenesExistentes != null && ordenesExistentes.stream()
	                .anyMatch(o -> o.getEstado() != null && "EN PROCESO".equalsIgnoreCase(o.getEstado().trim()));

	        if (existeOrdenEnProceso) {
	            String nombreUsuarioSession = (String) session.getAttribute("nombreUsuario");

	            model.addAttribute("error", 
	                "No se puede registrar una nueva orden de producción mientras exista una con estado 'EN PROCESO'.");
	            model.addAttribute("nombreUsuarioLogueado", nombreUsuarioSession);
	            model.addAttribute("listaplan", planService.listarPlan());
	            model.addAttribute("listaproductos", productoService.listarProductos());
	            model.addAttribute("listausuarios", usuarioService.listarUsuarios());

	            return "ordenproduccion/crearorden";
	        }

	        servicioAPI.guardarOrden(requestDto);
	        redirectAttributes.addFlashAttribute("success", "Registro guardado correctamente.");
	        
	        return "redirect:/ordenproduccion";
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Error al guardar la orden de producción: " + e.getMessage());
	        return "redirect:/ordenproduccion/crearorden";
	    }
	}

	@GetMapping("/editarorden")
	public String mostrarFormularioEditar(@RequestParam("idOrden") Integer idOrden, 
	                                     Model model, 
	                                     HttpSession session, 
	                                     RedirectAttributes redirectAttributes) {

		OrdenProduccionResponseDto ordenExistente = servicioAPI.buscarPorId(idOrden);
		Integer idUsuarioSession = (Integer) session.getAttribute("idUsuario");

		// Validar que la orden pertenezca al usuario en sesión
		if (ordenExistente.getIdUsuario() != null && !ordenExistente.getIdUsuario().equals(idUsuarioSession)) {
			redirectAttributes.addFlashAttribute("error", "No tienes permisos para modificar esta órden de producción.");
			return "redirect:/ordenproduccion";
		}

		OrdenProduccionRequestDto dto = new OrdenProduccionRequestDto();
		dto.setIdOrden(ordenExistente.getIdOrden());
		dto.setNumeroLote(ordenExistente.getNumeroLote());
		dto.setCantidadLote(ordenExistente.getCantidadLote());
		dto.setFechaInicio(ordenExistente.getFechaInicio());
		dto.setFechaFin(ordenExistente.getFechaFin());
		dto.setEstado(ordenExistente.getEstado());
		dto.setIdPlan(ordenExistente.getIdPlan());
		dto.setIdProducto(ordenExistente.getIdProducto());
		dto.setIdUsuario(ordenExistente.getIdUsuario());

		model.addAttribute("orden", dto);
		model.addAttribute("listaplan", planService.listarPlan());
		model.addAttribute("listaproductos", productoService.listarProductos()); 
		model.addAttribute("listausuarios", usuarioService.listarUsuarios());

		return "ordenproduccion/editarorden";
	}

	@PostMapping("/actualizar")
	public String actualizarOrden(@ModelAttribute("orden") OrdenProduccionRequestDto ordenDto,
	                              HttpSession session,
	                              RedirectAttributes redirectAttributes) {
		try {
			OrdenProduccionResponseDto ordenExistente = servicioAPI.buscarPorId(ordenDto.getIdOrden());

			if (ordenDto.getFechaInicio() == null && ordenExistente != null) {
				ordenDto.setFechaInicio(ordenExistente.getFechaInicio());
			}

			String estadoNuevo = ordenDto.getEstado();
			if ("COMPLETADO".equalsIgnoreCase(estadoNuevo) || "CANCELADO".equalsIgnoreCase(estadoNuevo)) {
				if (ordenExistente != null && ordenExistente.getFechaFin() != null) {
					// Si ya tenía una fecha de fin asignada previa, la mantiene; o asigna ahora si estaba nula
					ordenDto.setFechaFin(ordenExistente.getFechaFin());
				} else {
					ordenDto.setFechaFin(java.time.LocalDateTime.now());
				}
			} else {
				ordenDto.setFechaFin(null);
			}

			if (ordenDto.getIdUsuario() == null && ordenExistente != null) {
				ordenDto.setIdUsuario(ordenExistente.getIdUsuario());
			}

			servicioAPI.actualizarOrden(ordenDto.getIdOrden(), ordenDto);
			redirectAttributes.addFlashAttribute("success", "Órden de producción actualizada con éxito.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "Error al actualizar la orden de producción: " + e.getMessage());
		}
		return "redirect:/ordenproduccion";
	}

	@PostMapping("/eliminar/{id}")
	public String eliminarOrden(@PathVariable("id") Integer id, 
	                           HttpSession session, 
	                           RedirectAttributes redirectAttributes) {
		try {
			OrdenProduccionResponseDto orden = servicioAPI.buscarPorId(id);
			Integer idUsuarioSession = (Integer) session.getAttribute("idUsuario");

			if (orden.getIdUsuario() != null && !orden.getIdUsuario().equals(idUsuarioSession)) {
				redirectAttributes.addFlashAttribute("error", "No tienes permisos para eliminar esta órden de producción.");
				return "redirect:/ordenproduccion";
			}

			servicioAPI.eliminarOrden(id);
			redirectAttributes.addFlashAttribute("success", "Órden de fabricación eliminada correctamente.");
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("error", "No se pudo eliminar la órden de fabricación, existe un ensayo y dictamen asignados a esta órden.");
		}
		return "redirect:/ordenproduccion";
	}
}