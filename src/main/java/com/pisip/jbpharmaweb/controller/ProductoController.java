package com.pisip.jbpharmaweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pisip.jbpharmaweb.model.dto.request.ProductoRequestDto;
import com.pisip.jbpharmaweb.model.dto.request.UsuarioRequestDTO;
import com.pisip.jbpharmaweb.model.dto.response.ProductoResponseDto;
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
	public String leerpaginaeditar() {
		return "/producto/editarproducto";
	}

}
