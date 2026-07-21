package com.pisip.jbpharmaweb.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        // Verificar si la sesión existe y si hay un usuario logueado
        String usuarioLogueado = (session != null) ? (String) session.getAttribute("usuarioLogueado") : null;

        if (usuarioLogueado == null) {
            // Si no ha iniciado sesión, redirigir a la pantalla de login
            response.sendRedirect(request.getContextPath() + "/autenticacion?error=unauthorized");
            return false;
        }

        // Si inició sesión, permite el acceso a cualquier URL
        return true; 
    }
}