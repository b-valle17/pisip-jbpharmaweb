package com.pisip.jbpharmaweb.controller;

import java.util.List;
import java.util.Optional;

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

import com.pisip.jbpharmaweb.model.dto.request.ProductoRequestDto;
import com.pisip.jbpharmaweb.model.dto.request.UsuarioRequestDTO;
import com.pisip.jbpharmaweb.model.dto.response.ProductoResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.UsuarioResponseDTO;
import com.pisip.jbpharmaweb.service.IProductoService;

@Controller
@RequestMapping("/producto")
public class ProductoController {

	@Autowired
	private IProductoService servicioAPI;

	@GetMapping
	public String leerpagina(Model model) {
		List<ProductoResponseDto> datosAPI = servicioAPI.listarProductos();
		model.addAttribute("listaproductos", datosAPI);
		return "/producto/listarproductos";
	}

	@GetMapping("/crearproducto")
	public String leerpaginacrear(Model model) {
		model.addAttribute("producto", new ProductoRequestDto());
		return "/producto/crearproducto";
	}

	@PostMapping("/guardar")
	public String guardarProducto(@ModelAttribute ProductoRequestDto producto) {
		servicioAPI.guardarProducto(producto);
		return "redirect:/producto";
	}

	@GetMapping("/editarproducto")
	public String leerpaginaeditar(@RequestParam("idProducto") int idProducto, Model model) {
		Optional<ProductoResponseDto> productoOpt = servicioAPI.obtenerProductoPorId(idProducto);

		if (productoOpt.isEmpty()) {
			return "redirect:/producto?error=ProductoNoEncontrado";
		}

		ProductoResponseDto res = productoOpt.get();

		// Mapeamos los datos de la respuesta al DTO del formulario
		ProductoRequestDto formDto = new ProductoRequestDto();
		formDto.setIdProducto(res.getIdProducto());
		formDto.setNombreProducto(res.getNombreProducto());
		formDto.setDescripcion(res.getDescripcion());

		model.addAttribute("producto", formDto);

		return "/producto/editarproducto";
	}
	
	@PostMapping("/actualizar")
	public String actualizarProducto(@ModelAttribute ProductoRequestDto producto) {
	    servicioAPI.actualizarProducto(producto.getIdProducto(), producto);
	    return "redirect:/producto";
	}
	
	@PostMapping("/eliminar/{idProducto}")
	public String eliminarProducto(@PathVariable int idProducto, RedirectAttributes redirectAttributes) {
	    try {
	        servicioAPI.eliminarProducto(idProducto);
	        redirectAttributes.addFlashAttribute("success", "Producto eliminado correctamente.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el producto.");
	    }
	    return "redirect:/producto"; // Redirige de vuelta al listado
	}

}
