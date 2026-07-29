package com.pisip.jbpharmaweb.controller;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pisip.jbpharmaweb.model.dto.request.EnsayoLaboratorioRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoLaboratorioResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.OrdenProduccionResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.ProductoResponseDto;
import com.pisip.jbpharmaweb.service.iEnsayoLaboratorioService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ensayos")
public class EnsayoLaboratorioController {
    private final iEnsayoLaboratorioService servicio;
    private final WebClient webClient;

    public EnsayoLaboratorioController(iEnsayoLaboratorioService servicio, WebClient webClient) {
        this.servicio = servicio;
        this.webClient = webClient;
    }

    @GetMapping
    public String listar(Model model, HttpSession session) {
        List<EnsayoLaboratorioResponseDto> ensayos = servicio.listar();
        String usuario = nombreUsuario(session);
        model.addAttribute("ensayos", ensayos);
        model.addAttribute("puedeEditar", ensayos.stream().collect(Collectors.toMap(
                EnsayoLaboratorioResponseDto::getIdEnsayo,
                e -> usuario.equalsIgnoreCase(texto(e.getResponsable())), (a,b)->a)));
        List<OrdenProduccionResponseDto> ordenes = listarOrdenes();
        List<ProductoResponseDto> productos = listarProductos();
        model.addAttribute("ordenesPorId", ordenes.stream().collect(Collectors.toMap(OrdenProduccionResponseDto::getIdOrden, Function.identity(), (a,b)->a)));
        model.addAttribute("productosPorId", productos.stream().collect(Collectors.toMap(ProductoResponseDto::getIdProducto, Function.identity(), (a,b)->a)));
        return "ensayo/listaensayo";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model, HttpSession session) {
        EnsayoLaboratorioRequestDto dto = model.containsAttribute("ensayoError")
                ? (EnsayoLaboratorioRequestDto) model.getAttribute("ensayoError")
                : new EnsayoLaboratorioRequestDto();
        dto.setResponsable(nombreUsuario(session));
        if (dto.getFechaEnsayo() == null) {
            dto.setFechaEnsayo(LocalDateTime.now());
        }
        model.addAttribute("ensayo", dto);
        cargarRelaciones(model);
        return "ensayo/crearensayo";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("ensayo") EnsayoLaboratorioRequestDto dto,
            RedirectAttributes ra, HttpSession session) {
        dto.setIdEnsayo(null);
        dto.setCodigoEnsayo(null);
        dto.setResponsable(nombreUsuario(session));
        asignarProductoDesdeOrden(dto);
        try {
            servicio.guardar(dto);
            ra.addFlashAttribute("success", "Ensayo registrado correctamente.");
            return "redirect:/ensayos";
        } catch (Exception ex) {
            ra.addFlashAttribute("error", "No se pudo guardar el ensayo: " + extraerMensaje(ex));
            ra.addFlashAttribute("ensayoError", dto);
            return "redirect:/ensayos/nuevo";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable long id, Model model, HttpSession session, RedirectAttributes ra) {
        EnsayoLaboratorioResponseDto r = obligatorio(id);
        if (!esPropietario(r, session)) {
            ra.addFlashAttribute("error", "Solo el responsable que creó el ensayo puede editarlo.");
            return "redirect:/ensayos";
        }
        EnsayoLaboratorioRequestDto d = new EnsayoLaboratorioRequestDto();
        copiar(r, d);
        model.addAttribute("ensayo", d);
        cargarRelaciones(model);
        return "ensayo/editarensayo";
    }

    @PostMapping("/{id}/actualizar")
    public String actualizar(@PathVariable long id, @ModelAttribute("ensayo") EnsayoLaboratorioRequestDto dto,
            RedirectAttributes ra, HttpSession session) {
        EnsayoLaboratorioResponseDto actual = obligatorio(id);
        if (!esPropietario(actual, session)) {
            ra.addFlashAttribute("error", "No tiene permiso para modificar este ensayo.");
            return "redirect:/ensayos";
        }
        dto.setResponsable(actual.getResponsable());
        dto.setCodigoEnsayo(actual.getCodigoEnsayo());
        asignarProductoDesdeOrden(dto);
        servicio.actualizar(id, dto);
        ra.addFlashAttribute("success", "Ensayo actualizado correctamente.");
        return "redirect:/ensayos";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable long id, RedirectAttributes ra, HttpSession session) {
        EnsayoLaboratorioResponseDto actual = obligatorio(id);
        if (!esPropietario(actual, session)) {
            ra.addFlashAttribute("error", "No tiene permiso para eliminar este ensayo.");
            return "redirect:/ensayos";
        }
        servicio.eliminar(id);
        ra.addFlashAttribute("success", "Ensayo eliminado correctamente.");
        return "redirect:/ensayos";
    }

    private void asignarProductoDesdeOrden(EnsayoLaboratorioRequestDto dto) {
        OrdenProduccionResponseDto orden = listarOrdenes().stream()
                .filter(o -> o.getIdOrden().equals(dto.getIdOrden())).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("La orden seleccionada no existe."));
        Integer idProducto = orden.getIdProductoResuelto();
        if (idProducto == null) {
            throw new IllegalArgumentException("La orden seleccionada no tiene un producto asociado.");
        }
        dto.setIdProducto(idProducto);
    }

    private void cargarRelaciones(Model model) {
        List<OrdenProduccionResponseDto> ordenes = listarOrdenes();
        List<ProductoResponseDto> productos = listarProductos();

        Map<Integer, ProductoResponseDto> productosPorId = productos.stream()
                .filter(p -> p.getIdProducto() != null)
                .collect(Collectors.toMap(
                        ProductoResponseDto::getIdProducto,
                        Function.identity(),
                        (a, b) -> a));

        // La API de órdenes entrega el idProducto. Aquí se incorpora el objeto
        // ProductoResponseDto para que la vista muestre el nombre real y no
        // el texto genérico "Producto 1".
        ordenes.forEach(orden -> {
            Integer idProducto = orden.getIdProductoResuelto();
            if (orden.getProducto() == null && idProducto != null) {
                orden.setProducto(productosPorId.get(idProducto));
            }
        });

        model.addAttribute("ordenes", ordenes);
        model.addAttribute("productos", productos);
        model.addAttribute("productosPorId", productosPorId);
        model.addAttribute("nombresProductos", productosPorId.values().stream()
                .collect(Collectors.toMap(
                        ProductoResponseDto::getIdProducto,
                        p -> p.getNombreProducto() == null || p.getNombreProducto().isBlank()
                                ? "Producto " + p.getIdProducto()
                                : p.getNombreProducto(),
                        (a, b) -> a)));
    }

    private List<OrdenProduccionResponseDto> listarOrdenes() {
        return webClient.get().uri("/ordenProduccion").retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<OrdenProduccionResponseDto>>() {}).blockOptional().orElseGet(List::of);
    }
    private List<ProductoResponseDto> listarProductos() {
        return webClient.get().uri("/productos").retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductoResponseDto>>() {}).blockOptional().orElseGet(List::of);
    }
    private EnsayoLaboratorioResponseDto obligatorio(long id) {
        return servicio.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException("Ensayo no encontrado."));
    }
    private boolean esPropietario(EnsayoLaboratorioResponseDto e, HttpSession s) {
        return nombreUsuario(s).equalsIgnoreCase(texto(e.getResponsable()));
    }
    private String nombreUsuario(HttpSession s) {
        Object n = s.getAttribute("nombreUsuario");
        if (n == null || n.toString().isBlank()) throw new IllegalStateException("Sesión de usuario no válida.");
        return n.toString().trim();
    }
    private String texto(String v) { return v == null ? "" : v.trim(); }
    private String extraerMensaje(Exception ex) {
        Throwable c = ex;
        while (c != null) {
            if (c instanceof WebClientResponseException w && !w.getResponseBodyAsString().isBlank()) return w.getResponseBodyAsString();
            if (c.getCause() == null) break;
            c = c.getCause();
        }
        return c != null && c.getMessage() != null ? c.getMessage() : ex.getClass().getSimpleName();
    }
    private void copiar(EnsayoLaboratorioResponseDto r, EnsayoLaboratorioRequestDto d) {
        d.setIdEnsayo(r.getIdEnsayo()); d.setIdOrden(r.getIdOrden()); d.setIdProducto(r.getIdProducto());
        d.setCodigoEnsayo(r.getCodigoEnsayo()); d.setFechaEnsayo(r.getFechaEnsayo()); d.setResponsable(r.getResponsable());
        d.setObservacion(r.getObservacion()); d.setEstado(r.getEstado()); d.setCreadoEn(r.getCreadoEn());
    }
}
