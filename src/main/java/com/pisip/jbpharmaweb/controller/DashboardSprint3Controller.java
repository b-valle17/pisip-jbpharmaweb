package com.pisip.jbpharmaweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pisip.jbpharmaweb.service.iAlertaEnsayoService;
import com.pisip.jbpharmaweb.service.iEnsayoLaboratorioService;
import com.pisip.jbpharmaweb.service.iEnsayoVariableService;
import com.pisip.jbpharmaweb.service.iValidacionSemaforicaService;

@Controller
public class DashboardSprint3Controller {
    private final iEnsayoLaboratorioService ensayos;
    private final iEnsayoVariableService variables;
    private final iValidacionSemaforicaService validaciones;
    private final iAlertaEnsayoService alertas;

    public DashboardSprint3Controller(iEnsayoLaboratorioService ensayos,
            iEnsayoVariableService variables,
            iValidacionSemaforicaService validaciones,
            iAlertaEnsayoService alertas) {
        this.ensayos = ensayos;
        this.variables = variables;
        this.validaciones = validaciones;
        this.alertas = alertas;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        var listaEnsayos = ensayos.listar();
        var listaVariables = variables.listar();
        var listaValidaciones = validaciones.listar();
        var listaAlertas = alertas.listar();
        model.addAttribute("totalEnsayos", listaEnsayos.size());
        model.addAttribute("totalVariables", listaVariables.size());
        model.addAttribute("totalValidaciones", listaValidaciones.size());
        model.addAttribute("totalAlertas", listaAlertas.size());
        model.addAttribute("optimos", listaValidaciones.stream().filter(v -> "OPTIMO".equalsIgnoreCase(v.getResultado())).count());
        model.addAttribute("precauciones", listaValidaciones.stream().filter(v -> "PRECAUCION".equalsIgnoreCase(v.getResultado())).count());
        model.addAttribute("criticos", listaValidaciones.stream().filter(v -> "CRITICO".equalsIgnoreCase(v.getResultado())).count());
        model.addAttribute("alertasError", listaAlertas.stream().filter(a -> "ERROR".equalsIgnoreCase(a.getEstadoEnvio())).count());
        model.addAttribute("ensayosRecientes", listaEnsayos.stream().limit(8).toList());
        return "dashboard/sprint3";
    }
}
