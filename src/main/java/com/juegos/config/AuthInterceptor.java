package com.juegos.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        // Recursos públicos permitidos
        if (uri.startsWith("/login") ||
            uri.startsWith("/registro") ||
            uri.startsWith("/logout") ||
            uri.startsWith("/img/") ||
            uri.startsWith("/css/") ||
            uri.startsWith("/js/") ||
            uri.startsWith("/webjars/")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        String rol = (String) session.getAttribute("usuarioRol");
        if (rol == null) {
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        // Restricciones por Rol:
        // VENDEDOR: puede ver productos, proveedores, clientes y generar reportes,
        // pero NO puede modificar usuarios/clientes ni roles (rutas /clientes/nuevo, /clientes/guardar, /clientes/editar, /clientes/eliminar, /clientes/cambiar-rol)
        // tampoco puede modificar productos ni proveedores si no es ADMIN.
        if ("VENDEDOR".equalsIgnoreCase(rol)) {
            if (uri.startsWith("/clientes/nuevo") ||
                uri.startsWith("/clientes/guardar") ||
                uri.startsWith("/clientes/editar") ||
                uri.startsWith("/clientes/eliminar") ||
                uri.startsWith("/clientes/cambiar-rol") ||
                uri.startsWith("/productos/nuevo") ||
                uri.startsWith("/productos/guardar") ||
                uri.startsWith("/productos/editar") ||
                uri.startsWith("/productos/eliminar") ||
                uri.startsWith("/proveedores/nuevo") ||
                uri.startsWith("/proveedores/guardar") ||
                uri.startsWith("/proveedores/editar") ||
                uri.startsWith("/proveedores/eliminar")) {
                response.sendRedirect(request.getContextPath() + "/productos?errorAcceso=vendedor");
                return false;
            }
        }

        // CLIENTE común: solo puede ver el catálogo (/productos, /productos/filtrar, etc.), no reportes ni administración
        if ("CLIENTE".equalsIgnoreCase(rol)) {
            if (uri.startsWith("/proveedores") ||
                uri.startsWith("/clientes") ||
                uri.contains("/nuevo") ||
                uri.contains("/guardar") ||
                uri.contains("/editar") ||
                uri.contains("/eliminar") ||
                uri.contains("/reporte")) {
                response.sendRedirect(request.getContextPath() + "/productos");
                return false;
            }
        }

        return true;
    }
}
