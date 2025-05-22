package com.example.Unilink.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Unilink.utils.JwtUtil;

import java.io.IOException;

@Component
public class JwtRequestFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getRequestURI();

        // Excluir recursos estáticos para que carguen sin token
        if (path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/img/")
                || path.startsWith("/webjars/") ||
                path.equals("/")) {
            chain.doFilter(request, response);
            return;
        }

        String token = null;
        Cookie[] cookies = req.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if ("jwt".equals(c.getName())) {
                    token = c.getValue();
                    break;
                }
            }
        }

        boolean isLoginPage = path.equals("/entrar") || path.equals("/login");

        if (token != null && jwtUtil.validateToken(token)) {
            req.setAttribute("email", jwtUtil.extractEmail(token));
        } else if (!isLoginPage && !path.startsWith("/static")) {
            res.sendRedirect("/entrar");
            return;
        }

        chain.doFilter(request, response);
    }
}