package com.jetbrains.ebankingapp3.auth;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class SessionFilter implements Filter {
    private final SessionManager sessionManager;

    public SessionFilter(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String path = httpRequest.getRequestURI();

        // Allow login/logout and public endpoints without session
        if (path.startsWith("/auth/login") || path.startsWith("/auth/logout") ||
                path.startsWith("/public")) {
            chain.doFilter(request, response);
            return;
        }

        // Check session for other endpoints
        String sessionId = getSessionIdFromRequest(httpRequest);
        if (sessionId == null || sessionManager.getClientFromSession(sessionId) == null) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid session");
            return;
        }

        chain.doFilter(request, response);
    }

    private String getSessionIdFromRequest(HttpServletRequest request) {
        // Check cookie first
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (jakarta.servlet.http.Cookie cookie : cookies) {
                if ("SESSIONID".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        // Fallback to header
        return request.getHeader("X-Session-Id");
    }
}