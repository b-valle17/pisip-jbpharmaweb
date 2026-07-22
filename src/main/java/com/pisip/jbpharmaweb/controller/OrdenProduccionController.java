package com.pisip.jbpharmaweb.controller;

import java.util.List;

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
	public String leerpagina(Model model) {
		List<OrdenProduccionResponseDto> datosAPI = servicioAPI.listarOrden();
		model.addAttribute("listaorden", datosAPI);
		return "/ordenproduccion/listarorden"; 
	}
	@GetMapping("/crearorden")
    public String leerpaginacrear(Model model) {
        model.addAttribute("orden", new OrdenProduccionRequestDto());
        List<PlanProduccionResponseDto> planes = planService.listarPlan();
        List<ProductoResponseDto> productos = productoService.listarProductos(); 
        List<UsuarioResponseDTO> usuarios = usuarioService.listarUsuarios();
        model.addAttribute("listaplan", planes);           
        model.addAttribute("listaproductos", productos);  
        model.addAttribute("listausuarios", usuarios);     

        return "/ordenproduccion/crearorden";
    }
	
	@PostMapping("/guardar")
    public String guardarOrden(@ModelAttribute("orden") OrdenProduccionRequestDto requestDto, 
                               RedirectAttributes redirectAttributes) {
        try {

            servicioAPI.guardarOrden(requestDto);
            redirectAttributes.addFlashAttribute("success", "Registro guardado correctamente.");
            
            return "redirect:/ordenproduccion";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar la orden de producción: " + e.getMessage());
            return "redirect:/ordenproduccion/crearorden";
        }
    }

	
	@PostMapping("/eliminar/{id}")
	public String eliminarOrden(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
	    try {
	        servicioAPI.eliminarOrden(id);
	        redirectAttributes.addFlashAttribute("success", "Órden de fabricación eliminado correctamente.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "No se pudo eliminar la órden de fabricación.");
	    }
	    return "redirect:/ordenproduccion";
	}
	
	@GetMapping("/editarorden")
	public String mostrarFormularioEditar(@RequestParam("idOrden") Integer idOrden, Model model) {

	    OrdenProduccionResponseDto ordenExistente = servicioAPI.buscarPorId(idOrden);
	    
	    OrdenProduccionRequestDto dto = new OrdenProduccionRequestDto();
	    dto.setIdOrden(ordenExistente.getIdOrden());
	    dto.setNumeroLote(ordenExistente.getNumeroLote());
	    dto.setCantidadLote(ordenExistente.getCantidadLote());
	    dto.setFechaInicio(ordenExistente.getFechaInicio());
	    dto.setFechaFin(ordenExistente.getFechaFin());
	    dto.setEstado(ordenExistente.getEstado());

	    if (ordenExistente.getIdPlan() != null) {
	        dto.setIdPlan(ordenExistente.getIdPlan());
	    }
	    if (ordenExistente.getIdProducto() != null) {
	        dto.setIdProducto(ordenExistente.getIdProducto());
	    }
	    if (ordenExistente.getIdUsuario() != null) {
	        dto.setIdUsuario(ordenExistente.getIdUsuario());
	    }

	    model.addAttribute("orden", dto);
	    model.addAttribute("listaplan", planService.listarPlan());
	    model.addAttribute("listaproductos", productoService.listarProductos()); 
	    model.addAttribute("listausuarios", usuarioService.listarUsuarios());

	    return "/ordenproduccion/editarorden";
	}

	@PostMapping("/actualizar")
	public String actualizarOrden(@ModelAttribute("orden") OrdenProduccionRequestDto ordenDto,
	                              RedirectAttributes redirectAttributes) {
	    try {
	        servicioAPI.actualizarOrden(ordenDto.getIdOrden(), ordenDto);
	        redirectAttributes.addFlashAttribute("success", "Órden de producción actualizada con éxito.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Error al actualizar la orden de producción.");
	    }
	    return "redirect:/ordenproduccion";
	}
}
