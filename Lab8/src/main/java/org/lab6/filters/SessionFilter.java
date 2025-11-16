package org.lab6.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebFilter("/*")
public class SessionFilter implements Filter {

    private static final Logger logger = Logger.getLogger(SessionFilter.class.getName());

    private void sendJsonError(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        resp.setContentType("application/json");
        try (PrintWriter out = resp.getWriter()) {
            out.print("{\"error\":\"" + message + "\"}");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI();

        // Пропускаем запросы к /auth
        if (path.contains("/auth")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);

        // Разрешаем только GET, если нет авторизации
        if (session == null || session.getAttribute("user") == null) {
            if ("GET".equalsIgnoreCase(req.getMethod())) {
                chain.doFilter(request, response);
            } else {
                sendJsonError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Login required");
            }
            return;
        }

        // Ведём счётчик запросов для активной сессии
        Integer counter = (Integer) session.getAttribute("counter");
        if (counter == null) counter = 0;
        session.setAttribute("counter", counter + 1);
        resp.setHeader("X-Request-Count", String.valueOf(counter + 1));

        chain.doFilter(request, response);
    }
}