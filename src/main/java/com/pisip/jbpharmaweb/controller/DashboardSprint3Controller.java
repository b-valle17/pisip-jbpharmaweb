package com.pisip.jbpharmaweb.controller;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pisip.jbpharmaweb.model.dto.response.AlertaEnsayoResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.DictamenLoteResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoLaboratorioResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.EnsayoVariableResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.IndicadorKpiResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.IndicadorKpiResumenDto;
import com.pisip.jbpharmaweb.model.dto.response.PlanProduccionResponseDto;
import com.pisip.jbpharmaweb.model.dto.response.UsuarioResponseDTO;
import com.pisip.jbpharmaweb.model.dto.response.ValidacionSemaforicaResponseDto;
import com.pisip.jbpharmaweb.service.iAlertaEnsayoService;
import com.pisip.jbpharmaweb.service.iEnsayoLaboratorioService;
import com.pisip.jbpharmaweb.service.iEnsayoVariableService;
import com.pisip.jbpharmaweb.service.IIndicadorKpiService;
import com.pisip.jbpharmaweb.service.IDictamenLoteService;
import com.pisip.jbpharmaweb.service.IPlanProduccionService;
import com.pisip.jbpharmaweb.service.IUsuarioService;
import com.pisip.jbpharmaweb.service.iValidacionSemaforicaService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardSprint3Controller {
    private final iEnsayoLaboratorioService ensayos;
    private final iEnsayoVariableService variables;
    private final iValidacionSemaforicaService validaciones;
    private final iAlertaEnsayoService alertas;
    private final IPlanProduccionService planes;
    private final IDictamenLoteService dictamenes;
    private final IIndicadorKpiService indicadoresKpi;
    private final IUsuarioService usuarios;

    public DashboardSprint3Controller(iEnsayoLaboratorioService ensayos,
            iEnsayoVariableService variables,
            iValidacionSemaforicaService validaciones,
            iAlertaEnsayoService alertas,
            IPlanProduccionService planes,
            IDictamenLoteService dictamenes,
            IIndicadorKpiService indicadoresKpi,
            IUsuarioService usuarios) {
        this.ensayos = ensayos;
        this.variables = variables;
        this.validaciones = validaciones;
        this.alertas = alertas;
        this.planes = planes;
        this.dictamenes = dictamenes;
        this.indicadoresKpi = indicadoresKpi;
        this.usuarios = usuarios;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String rolUsuario = obtenerRol(session);

        if (esRol(rolUsuario, "ADMINISTRADOR")) {
            return "redirect:/dashboard/administrador";
        }
        if (esRol(rolUsuario, "SUPERVISOR")) {
            return "redirect:/dashboard/supervisor";
        }
        if (esRol(rolUsuario, "GERENTE")) {
            return "redirect:/dashboard/gerente";
        }
        if (esRol(rolUsuario, "ANALISTA")) {
            return "redirect:/dashboard/analista";
        }

        cargarResumenComun(model);
        return "dashboard/sprint3";
    }

    @GetMapping("/dashboard/administrador")
    public String dashboardAdministrador(Model model) {
        List<UsuarioResponseDTO> listaUsuarios = usuarios.listarUsuarios();
        List<UsuarioResponseDTO> usuariosRecientes = listaUsuarios.stream().limit(6).toList();
        List<UsuarioResponseDTO> usuariosActivos = listaUsuarios.stream()
                .filter(usuario -> usuario.isEstadoUsuario())
                .toList();
        List<UsuarioResponseDTO> usuariosInactivos = listaUsuarios.stream()
                .filter(usuario -> !usuario.isEstadoUsuario())
                .toList();
        List<com.pisip.jbpharmaweb.model.dto.response.RolResponseDto> listaRoles = usuarios.listarRoles();

        model.addAttribute("totalUsuarios", listaUsuarios.size());
        model.addAttribute("totalUsuariosActivos", usuariosActivos.size());
        model.addAttribute("totalUsuariosInactivos", usuariosInactivos.size());
        model.addAttribute("totalRoles", listaRoles.size());
        model.addAttribute("usuariosRecientes", usuariosRecientes);
        model.addAttribute("usuariosActivosRecientes", usuariosActivos.stream().limit(4).toList());
        model.addAttribute("roles", listaRoles);
        model.addAttribute("usuarios", listaUsuarios);

        return "dashboard/administrador";
    }

    @GetMapping("/dashboard/supervisor")
    public String dashboardSupervisor(Model model) {
        cargarResumenComun(model);

        List<DictamenLoteResponseDto> listaDictamenes = dictamenes.listarDictamenLote();
        List<AlertaEnsayoResponseDto> listaAlertas = alertas.listar();

        model.addAttribute("totalDictamenes", listaDictamenes.size());
        model.addAttribute("dictamenesPendientes", contarPorEstado(listaDictamenes,
                DictamenLoteResponseDto::getEstado, "PENDIENTE"));
        model.addAttribute("dictamenesAceptados", contarPorEstado(listaDictamenes,
                DictamenLoteResponseDto::getEstado, "ACEPTADO"));
        model.addAttribute("dictamenesRechazados", contarPorEstado(listaDictamenes,
                DictamenLoteResponseDto::getEstado, "RECHAZADO"));
        model.addAttribute("alertasPendientes", contarPorEstado(listaAlertas,
                AlertaEnsayoResponseDto::getEstadoEnvio, "PENDIENTE"));
        model.addAttribute("alertasRecientes", listaAlertas.stream().limit(5).toList());
        model.addAttribute("dictamenesRecientes", listaDictamenes.stream().limit(5).toList());

        return "dashboard/supervisor";
    }

    @GetMapping("/dashboard/gerente")
    public String dashboardGerente(Model model) {
        List<PlanProduccionResponseDto> listaPlanes = planes.listarPlan();
        List<DictamenLoteResponseDto> listaDictamenes = dictamenes.listarDictamenLote();
        List<IndicadorKpiResponseDto> listaIndicadores = indicadoresKpi.listarIndicadorKpi();
        List<UsuarioResponseDTO> listaUsuarios = usuarios.listarUsuarios();
        IndicadorKpiResumenDto resumenKpi = indicadoresKpi.obtenerResumen();

        model.addAttribute("totalPlanes", listaPlanes.size());
        model.addAttribute("planesRecientes", listaPlanes.stream().limit(5).toList());
        model.addAttribute("totalDictamenes", listaDictamenes.size());
        model.addAttribute("dictamenesRecientes", listaDictamenes.stream().limit(5).toList());
        model.addAttribute("totalIndicadoresKpi", listaIndicadores.size());
        model.addAttribute("totalUsuarios", listaUsuarios.size());
        model.addAttribute("resumenKpi", resumenKpi);

        return "dashboard/gerente";
    }

    @GetMapping("/dashboard/analista")
    public String dashboardAnalista(Model model) {
        cargarResumenComun(model);
        return "dashboard/analista";
    }

    private void cargarResumenComun(Model model) {
        List<EnsayoLaboratorioResponseDto> listaEnsayos = ensayos.listar();
        List<EnsayoVariableResponseDto> listaVariables = variables.listar();
        List<ValidacionSemaforicaResponseDto> listaValidaciones = validaciones.listar();
        List<AlertaEnsayoResponseDto> listaAlertas = alertas.listar();

        model.addAttribute("totalEnsayos", listaEnsayos.size());
        model.addAttribute("totalVariables", listaVariables.size());
        model.addAttribute("totalValidaciones", listaValidaciones.size());
        model.addAttribute("totalAlertas", listaAlertas.size());
        model.addAttribute("optimos", contarPorEstado(listaValidaciones,
                ValidacionSemaforicaResponseDto::getResultado, "OPTIMO"));
        model.addAttribute("precauciones", contarPorEstado(listaValidaciones,
                ValidacionSemaforicaResponseDto::getResultado, "PRECAUCION"));
        model.addAttribute("criticos", contarPorEstado(listaValidaciones,
                ValidacionSemaforicaResponseDto::getResultado, "CRITICO"));
        model.addAttribute("alertasError", contarPorEstado(listaAlertas,
                AlertaEnsayoResponseDto::getEstadoEnvio, "ERROR"));
        model.addAttribute("ensayosRecientes", listaEnsayos.stream().limit(5).toList());
    }

    private String obtenerRol(HttpSession session) {
        Object rol = session.getAttribute("rolUsuario");
        return rol != null ? rol.toString() : "";
    }

    private boolean esRol(String rolActual, String rolEsperado) {
        return rolActual != null && rolEsperado.equalsIgnoreCase(rolActual);
    }

    private <T> long contarPorEstado(List<T> elementos, Function<T, String> selector, String estadoEsperado) {
        return elementos.stream()
                .map(selector)
                .filter(estado -> estado != null && estadoEsperado.equalsIgnoreCase(estado.trim()))
                .count();
    }
}
