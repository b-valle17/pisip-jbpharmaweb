package com.pisip.jbpharmaweb.controller;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pisip.jbpharmaweb.model.dto.request.AlertaEnsayoRequestDto;
import com.pisip.jbpharmaweb.model.dto.response.AlertaEnsayoResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoLaboratorioResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoVariableResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.ValidacionSemaforicaResponseDto;
import com.pisip.jbpharmaweb.service.IEmailService;
import com.pisip.jbpharmaweb.service.iAlertaEnsayoService;
import com.pisip.jbpharmaweb.service.iEnsayoLaboratorioService;
import com.pisip.jbpharmaweb.service.iEnsayoVariableService;
import com.pisip.jbpharmaweb.service.iValidacionSemaforicaService;

@Controller
@RequestMapping("/alertas")
public class AlertaEnsayoController {

    private final iAlertaEnsayoService servicio;
    private final iValidacionSemaforicaService validacionServicio;
    private final IEmailService emailService;
    private final iEnsayoLaboratorioService ensayoServicio;
    private final iEnsayoVariableService variableServicio;

    public AlertaEnsayoController(
            iAlertaEnsayoService servicio,
            iValidacionSemaforicaService validacionServicio,
            IEmailService emailService,
            iEnsayoLaboratorioService ensayoServicio,
            iEnsayoVariableService variableServicio) {
        this.servicio = servicio;
        this.validacionServicio = validacionServicio;
        this.emailService = emailService;
        this.ensayoServicio = ensayoServicio;
        this.variableServicio = variableServicio;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("alertas", servicio.listar());
        return "alerta/listaalerta";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        AlertaEnsayoRequestDto alerta = new AlertaEnsayoRequestDto();
        alerta.setTipoAlerta("EMAIL");
        alerta.setEstadoEnvio("PENDIENTE");
        model.addAttribute("alerta", alerta);
        cargarDatosFormulario(model);
        return "alerta/detallealerta";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("alerta") AlertaEnsayoRequestDto dto, RedirectAttributes ra) {
        dto.setIdAlerta(null);
        dto.setTipoAlerta("EMAIL");
        dto.setEstadoEnvio("PENDIENTE");
        dto.setFechaEnvio(null);
        AlertaEnsayoResponseDto guardada = servicio.guardar(dto);
        try {
            emailService.enviarAlertaEnsayo(dto.getDestinatario(), dto.getAsunto(), dto.getMensaje());
            dto.setEstadoEnvio("ENVIADO");
            dto.setFechaEnvio(LocalDateTime.now());
            servicio.actualizar(guardada.getIdAlerta(), dto);
            ra.addFlashAttribute("success", "Alerta enviada correctamente a " + dto.getDestinatario() + ".");
        } catch (Exception ex) {
            dto.setEstadoEnvio("ERROR");
            dto.setFechaEnvio(null);
            servicio.actualizar(guardada.getIdAlerta(), dto);
            ra.addFlashAttribute("error", "La alerta se registró, pero el correo no pudo enviarse: " + mensajeSeguro(ex));
        }
        return "redirect:/alertas";
    }

    @PostMapping("/{id}/enviar")
    public String enviar(@PathVariable long id, RedirectAttributes ra) {
        AlertaEnsayoResponseDto alerta = servicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Alerta no encontrada: " + id));
        AlertaEnsayoRequestDto actualizacion = new AlertaEnsayoRequestDto();
        copiar(alerta, actualizacion);

        try {
            if (!"EMAIL".equalsIgnoreCase(alerta.getTipoAlerta())) {
                throw new IllegalArgumentException("Solo las alertas de tipo EMAIL pueden enviarse por correo.");
            }
            emailService.enviarAlertaEnsayo(alerta.getDestinatario(), alerta.getAsunto(), alerta.getMensaje());
            actualizacion.setEstadoEnvio("ENVIADO");
            actualizacion.setFechaEnvio(LocalDateTime.now());
            servicio.actualizar(id, actualizacion);
            ra.addFlashAttribute("success", "Correo enviado correctamente a " + alerta.getDestinatario() + ".");
        } catch (Exception ex) {
            actualizacion.setEstadoEnvio("ERROR");
            actualizacion.setFechaEnvio(null);
            servicio.actualizar(id, actualizacion);
            ra.addFlashAttribute("error", "No se pudo enviar el correo: " + mensajeSeguro(ex));
        }
        return "redirect:/alertas";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable long id, Model model) {
        AlertaEnsayoResponseDto r = servicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Registro no encontrado"));
        AlertaEnsayoRequestDto d = new AlertaEnsayoRequestDto();
        copiar(r, d);
        model.addAttribute("alerta", d);
        cargarDatosFormulario(model);
        return "alerta/detallealerta";
    }

    @PostMapping("/{id}/actualizar")
    public String actualizar(@PathVariable long id,
            @ModelAttribute("alerta") AlertaEnsayoRequestDto dto,
            RedirectAttributes ra) {
        AlertaEnsayoResponseDto actual = servicio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Registro no encontrado"));
        dto.setFechaGeneracion(actual.getFechaGeneracion());
        dto.setFechaEnvio("ENVIADO".equalsIgnoreCase(dto.getEstadoEnvio())
                ? (actual.getFechaEnvio() != null ? actual.getFechaEnvio() : LocalDateTime.now())
                : null);
        servicio.actualizar(id, dto);
        ra.addFlashAttribute("success", "Registro actualizado correctamente.");
        return "redirect:/alertas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable long id, RedirectAttributes ra) {
        servicio.eliminar(id);
        ra.addFlashAttribute("success", "Registro eliminado correctamente.");
        return "redirect:/alertas";
    }

    private void cargarDatosFormulario(Model model) {
        java.util.List<EnsayoVariableResponseDto> variables = variableServicio.listar();
        java.util.List<ValidacionSemaforicaResponseDto> validaciones = validacionServicio.listar();

        Set<Long> variablesValidadas = validaciones.stream()
                .map(ValidacionSemaforicaResponseDto::getIdVariable)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());
        Set<Long> ensayosConValidacion = variables.stream()
                .filter(v -> variablesValidadas.contains(v.getIdVariable()))
                .map(EnsayoVariableResponseDto::getIdEnsayo)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toSet());
        java.util.List<EnsayoLaboratorioResponseDto> ensayos = ensayoServicio.listar().stream()
                .filter(e -> ensayosConValidacion.contains(e.getIdEnsayo()))
                .toList();

        model.addAttribute("ensayos", ensayos);
        model.addAttribute("variables", variables);
        model.addAttribute("validaciones", validaciones);
    }

    private void copiar(AlertaEnsayoResponseDto r, AlertaEnsayoRequestDto d) {
        d.setIdAlerta(r.getIdAlerta());
        d.setIdValidacion(r.getIdValidacion());
        d.setTipoAlerta(r.getTipoAlerta());
        d.setDestinatario(r.getDestinatario());
        d.setAsunto(r.getAsunto());
        d.setMensaje(r.getMensaje());
        d.setEstadoEnvio(r.getEstadoEnvio());
        d.setFechaGeneracion(r.getFechaGeneracion());
        d.setFechaEnvio(r.getFechaEnvio());
    }

    private String mensajeSeguro(Exception ex) {
        return ex.getMessage() == null || ex.getMessage().isBlank()
                ? ex.getClass().getSimpleName()
                : ex.getMessage();
    }
}
